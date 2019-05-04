package controlers;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.WireWorld;
import utils.Utils;
import views.CellularAutomatonView;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.paint.Color.BLUE;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public class Controller {
    protected Canvas canvas;

    protected Slider zoomSlider;
    protected ToggleButton autoRunToggleButton;
    protected Button previousGenerationButton;
    protected Button nextGenerationButton;

    protected Spinner widthSpinner;

    protected Spinner heightSpinner;
    protected Button randomButton;
    protected Button emptyButton;
    protected Button saveButton;
    protected Button loadButton;

    protected Label generationNumberLabel;

    protected CellularAutomatonView cellularAutomatonView;

    public Controller(){};

    public Controller(Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel) {
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

        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);

        //cellularAutomatonView.generationNumberProperty().addListener(this::generationNumberChanged);
        //generationNumberLabel.textProperty().bind(cellularAutomatonView.generationNumberProperty().asString());

        zoomSlider.valueProperty().addListener(this::zoomSliderChanged);
        nextGenerationButton.setOnAction(this::nextGeneration);
        previousGenerationButton.setOnAction(this::previousGeneration);
        emptyButton.setOnAction(this::clearBoard);

    }

    protected void zoomSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
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

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

}