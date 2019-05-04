package controlers;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.GameOfLife;
import models.WireWorld;
import utils.Utils;
import views.CellularAutomatonView;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.BLUE;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public class WireWorldController {
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

    private final RadioButton emptyRadioButton;
    private final RadioButton tailRadioButton;
    private final RadioButton headRadioButton;
    private final RadioButton conductorRadioButton;

    private final CellularAutomatonView cellularAutomatonView;

    public WireWorldController(Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel, RadioButton emptyRadioButton, RadioButton tailRadioButton, RadioButton headRadioButton, RadioButton conductorRadioButton) {
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
        this.emptyRadioButton = emptyRadioButton;
        this.headRadioButton = headRadioButton;
        this.tailRadioButton = tailRadioButton;
        this.conductorRadioButton = conductorRadioButton;

        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);

//        GameOfLife gameOfLife = new GameOfLife(10, 10);
        Map<WireWorld.CellStates, Paint> coloring = new HashMap<>();
        coloring.put(WireWorld.CellStates.EMPTY, Color.BLACK);
        coloring.put(WireWorld.CellStates.HEAD, BLUE);
        coloring.put(WireWorld.CellStates.TAIL, Color.RED);
        coloring.put(WireWorld.CellStates.CONDUCTOR, Color.YELLOW);
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
        WireWorld.CellStates selectedState = getSelectedState();
        cellularAutomatonView.setCellToValue(event.getX(), event.getY(), getSelectedState());
    }

    private WireWorld.CellStates getSelectedState() {
        if (emptyRadioButton.isSelected())
            return WireWorld.CellStates.EMPTY;
        else if (headRadioButton.isSelected())
            return WireWorld.CellStates.HEAD;
        else if (tailRadioButton.isSelected())
            return WireWorld.CellStates.TAIL;
        else
            return WireWorld.CellStates.CONDUCTOR;
    }

    private void randomizeBoard(Event event) {
        int width = (int) widthSpinner.getValue();
        int height = (int) heightSpinner.getValue();


        if (!cellularAutomatonView.hasCellularAutomaton()
                || cellularAutomatonView.getColumnCount() != width
                || cellularAutomatonView.getRowCount() != height) {
            WireWorld wireWorld = new WireWorld(width, height);
            cellularAutomatonView.setCellularAutomaton(wireWorld);
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

        WireWorld wireWorld = new WireWorld(width, height);
        wireWorld.clear();
        cellularAutomatonView.setCellularAutomaton(wireWorld);

    }
}