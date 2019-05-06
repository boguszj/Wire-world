package controlers;

import javafx.application.Platform;
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

import static java.lang.Math.min;
import static javafx.scene.paint.Color.BLUE;
import static jdk.nashorn.internal.objects.NativeMath.max;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public class Controller {
    protected Canvas canvas;

    protected Slider zoomSlider;
    protected Slider speedSlider;
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

    private boolean running;
    private Thread t;
    private long delay;

    public Controller(){};

    public Controller(Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel) {
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
        this.delay = (long)speedSlider.getValue();

        createThread();

        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);

//        cellularAutomatonView.generationNumberProperty().addListener(this::generationNumberChanged);
//        generationNumberLabel.textProperty().bind(cellularAutomatonView.generationNumberProperty().asString());

        zoomSlider.valueProperty().addListener(this::zoomSliderChanged);
        speedSlider.valueProperty().addListener(this::speedSliderChanged);
        nextGenerationButton.setOnAction(this::nextGeneration);
        previousGenerationButton.setOnAction(this::previousGeneration);
        autoRunToggleButton.setOnAction(this::play);

    }

    protected void shrinkSlider(){
        double max = min(100, 8000 / max(widthSpinner.getValue(), heightSpinner.getValue()));
        zoomSlider.setMax(max);
        if(zoomSlider.getValue() > max)
            zoomSlider.setValue(max);
    }

    protected void enableButtons(){
        nextGenerationButton.setDisable(false);
        autoRunToggleButton.setDisable(false);
    }

    private void createThread(){
        t = new Thread(new Runnable() {
            public void run(){
                while(running) {
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            cellularAutomatonView.nextGeneration();
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
        cellularAutomatonView.setCellSize(newValue.doubleValue());
    }

    private void speedSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        this.delay = newValue.longValue();
    }

    protected void generationNumberChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
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

    private void play(Event event) {
        if(!running){
            running = true;
            t.start();
        }
        else{
            running = false;
            try {
                t.join(); // wait for the thread to stop
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createThread();
        }
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

}