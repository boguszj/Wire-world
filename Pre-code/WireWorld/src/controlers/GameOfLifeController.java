package controlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.GameOfLife;
import utils.Utils;
import views.CellularAutomatonView;

import java.util.HashMap;
import java.util.Map;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public class GameOfLifeController {
    private final Canvas canvas;

    private final Slider zoomSlider;
    private final ToggleButton autoRunToggleButton;
    private final Button previousGenerationButton;
    private final Button nextGenerationButton;

    private final Spinner widthSpinner;

    private final Spinner heightSpinner;
    private final Button randomButton;
    private final Button emptyButton;
    private final Button saveButton;
    private final Button loadButton;

    private final Label generationNumberLabel;

    private final RadioButton aliveRadioButton;
    private final RadioButton deadRadioButton;

    private final CellularAutomatonView cellularAutomatonView;

    public GameOfLifeController(Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel, RadioButton aliveRadioButton, RadioButton deadRadioButton) {
        this.canvas = canvas;
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
        this.aliveRadioButton = aliveRadioButton;
        this.deadRadioButton = deadRadioButton;

        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);

//        GameOfLife gameOfLife = new GameOfLife(10, 10);
        Map<GameOfLife.CellStates, Paint> coloring = new HashMap<>();
        coloring.put(GameOfLife.CellStates.DEAD, Color.BLACK);
        coloring.put(GameOfLife.CellStates.ALIVE, Color.WHITE);
        cellularAutomatonView = new CellularAutomatonView(canvas, coloring, zoomSlider.getValue());
//        cellularAutomatonView.randomize(); //Start with random view

        //Listen for change in generation number
        cellularAutomatonView.generationNumberProperty().addListener(this::generationNumberChanged);
        //Bind it's value to the label
        generationNumberLabel.textProperty().bind(cellularAutomatonView.generationNumberProperty().asString());

        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, this::canvasClicked);
        zoomSlider.valueProperty().addListener(this::zoomSliderChanged);

        randomButton.setOnAction(this::randomizeBoard);
        nextGenerationButton.setOnAction(this::nextGeneration);
        previousGenerationButton.setOnAction(this::previousGeneration);
        emptyButton.setOnAction(this::clearBoard);
    }

    private void zoomSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        cellularAutomatonView.setCellSize(newValue.doubleValue());
    }

    private void generationNumberChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (newValue.intValue() <= 0) {
            previousGenerationButton.setDisable(true);
        }
        else {
            previousGenerationButton.setDisable(false);
        }
    }

    private void canvasClicked(MouseEvent event) {
        GameOfLife.CellStates selectedState = getSelectedState();
        cellularAutomatonView.setCellToValue(event.getX(), event.getY(), getSelectedState());
    }

    private GameOfLife.CellStates getSelectedState() {
        if (aliveRadioButton.isSelected())
            return GameOfLife.CellStates.ALIVE;
        else
            return GameOfLife.CellStates.DEAD;
    }

    private void randomizeBoard(Event event) {
        int width = (int) widthSpinner.getValue();
        int height = (int) heightSpinner.getValue();

        if (!cellularAutomatonView.hasCellularAutomaton()
                || cellularAutomatonView.getColumnCount() != width
                || cellularAutomatonView.getRowCount() != height) {
            GameOfLife gameOfLife = new GameOfLife(width, height);
            cellularAutomatonView.setCellularAutomaton(gameOfLife);
        }

        cellularAutomatonView.randomize();
    }

    private void nextGeneration(Event event) {
        cellularAutomatonView.nextGeneration();
    }

    private void previousGeneration(Event event) {
        cellularAutomatonView.previousGeneration();
    }

    private void clearBoard(Event event) {
        int width = (int) widthSpinner.getValue();
        int height = (int) heightSpinner.getValue();

        GameOfLife gameOfLife = new GameOfLife(width, height);
        gameOfLife.clear();
        cellularAutomatonView.setCellularAutomaton(gameOfLife);
    }
}