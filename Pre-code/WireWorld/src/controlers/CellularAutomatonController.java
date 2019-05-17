package controlers;

import static utils.Utils.*;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import models.CellularAutomaton;
import utils.Utils;
import views.CellularAutomatonView;
import views.FXCellularAutomatonView;

import java.util.Map;

import static java.lang.Math.min;
import static jdk.nashorn.internal.objects.NativeMath.max;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public abstract class CellularAutomatonController<T extends Enum> {
    protected Canvas canvas;

    protected Slider zoomSlider;
    protected Slider speedSlider;
    protected ToggleButton autoRunToggleButton;
    protected Button previousGenerationButton;
    protected Button nextGenerationButton;

    protected Spinner<Integer> widthSpinner;
    protected Spinner<Integer> heightSpinner;
    protected Button randomButton;
    protected Button emptyButton;
    protected Button saveButton;
    protected Button loadButton;

    protected Label generationNumberLabel;

    protected CellularAutomaton cellularAutomaton;
    protected CellularAutomatonView<T> cellularAutomatonView;

    private boolean running;
    private Thread t;
    private long delay;

    public CellularAutomatonController(Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel) {
        this.canvas = canvas;
        this.speedSlider = speedSlider;
        this.zoomSlider = zoomSlider;
        this.autoRunToggleButton = autoRunToggleButton;
        this.previousGenerationButton = previousGenerationButton;
        this.nextGenerationButton = nextGenerationButton;
        this.widthSpinner = widthSpinner;
        this.heightSpinner = heightSpinner;
        this.randomButton = randomButton;
        this.emptyButton = emptyButton;
        this.saveButton = saveButton;
        this.loadButton = loadButton;
        this.generationNumberLabel = generationNumberLabel;
        this.running = false;
        this.delay = (long) speedSlider.getValue();

        cellularAutomatonView = new FXCellularAutomatonView<>(canvas, getColoring());

        createThread();

        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);

        generationNumberLabel.textProperty().addListener(this::generationNumberChanged);

        zoomSlider.valueProperty().addListener(this::zoomSliderChanged);
        speedSlider.valueProperty().addListener(this::speedSliderChanged);
        nextGenerationButton.setOnAction(this::nextGeneration);
        previousGenerationButton.setOnAction(this::previousGeneration);
        autoRunToggleButton.setOnAction(this::play);
        randomButton.setOnAction(this::randomizeBoard);
        emptyButton.setOnAction(this::clearBoard);
        canvas.setOnMouseClicked(this::canvasClicked);
        canvas.setOnMouseDragged(this::canvasClicked);
    }

    /**
     * Each controllers should create CellularAutomaton of type that it'll use
     *
     * @return instance of CellularAutomaton specific to given controller
     */
    protected abstract CellularAutomaton creteCellularAutomaton();

    /**
     * Mapping of each cellular automaton state to corresponding colour should be returned
     *
     * @return mapping of each cellular automaton state to corresponding colour
     */
    protected abstract Map<T, Paint> getColoring();

    /**
     * CellularAutomatonController should check which state is selected from input section and return it
     * @return state selected by a user
     */
    protected abstract T getSelectedState();

    /**
     * Seat cellular automaton to random state and display it
     * @param event <b>Not used</b>
     */
    protected void randomizeBoard(Event event) {
        int width = widthSpinner.getValue();
        int height = heightSpinner.getValue();

        enableButtons();
        shrinkSlider();

        if (cellularAutomaton == null
                || cellularAutomaton.getWidth() != width
                || cellularAutomaton.getHeight() != height) {
            cellularAutomaton = creteCellularAutomaton();
            generationNumberLabel.textProperty().bind(cellularAutomaton.currentGenerationProperty().asString());
        }

        cellularAutomaton.randomize();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    /**
     * Seat cellular automaton to clear state and display it
     * @param event <b>Not used</b>
     */
    protected void clearBoard(Event event) {
        enableButtons();
        shrinkSlider();

        cellularAutomaton = creteCellularAutomaton();
        generationNumberLabel.textProperty().bind(cellularAutomaton.currentGenerationProperty().asString());
        cellularAutomaton.clear();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    /**
     * Change value of clicked cell to value selected by the user
     * @param event Used for extracting mouse coordinates
     */
    protected void canvasClicked(MouseEvent event) {
        T selectedState = getSelectedState();
        final int row = (int) (event.getY() / zoomSlider.getValue());
        final int column = (int) (event.getX() / zoomSlider.getValue());

        cellularAutomaton.setCell(row, column, selectedState);
        cellularAutomatonView.drawCell(selectedState, column, row, zoomSlider.getValue());
    }

    protected void shrinkSlider() {
        double Max = myMin(8000, 8000 / myMax(widthSpinner.getValue(), heightSpinner.getValue()));
        double Min = myMax(500.0 / heightSpinner.getValue(), 1150.0 / widthSpinner.getValue());
        System.out.println(Min);
        System.out.println(Max);
        zoomSlider.setValue(Max);
        zoomSlider.setMax(Max);
        if (Min < Max) {
            zoomSlider.setValue(Min);
        }
    }

    protected void enableButtons() {
        nextGenerationButton.setDisable(false);
        autoRunToggleButton.setDisable(false);
    }

    private void createThread() {
        t = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            nextGeneration(null);
                            System.out.println(delay);
                        }
                    });
                    try {
                        Thread.sleep(1000 / delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
    }

    private void zoomSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        cellularAutomatonView.draw(cellularAutomaton, newValue.doubleValue());
    }

    private void speedSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        this.delay = newValue.longValue();
    }

    protected void generationNumberChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (Integer.parseInt(newValue) <= 0) {
            previousGenerationButton.setDisable(true);
        } else {
            previousGenerationButton.setDisable(false);
        }
    }

    /**
     * Move cellular automaton to the next state and draw it
     * @param event <b>Not used</b>
     */
    private void nextGeneration(Event event) {
        cellularAutomaton.nextGeneration();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    /**
     * Move cellular automaton to the previous state and draw it
     * @param event <b>Not used</b>
     */
    private void previousGeneration(Event event) {
        cellularAutomaton.previousGeneration();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    private void play(Event event) {
        if (!running) {
            running = true;
            t.start();
        } else {
            running = false;
            try {
                t.join(); // wait for the thread to stop
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createThread();
        }
    }
}