package controlers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.GameOfLife;
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

    private GameOfLife gameOfLife;
    private CellularAutomatonView cellularAutomatonView;

    public GameOfLifeController(Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel) {
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

        gameOfLife = new GameOfLife(10, 10);
        Map<GameOfLife.CellStates, Paint> coloring = new HashMap<>();
        coloring.put(GameOfLife.CellStates.DEAD, Color.BLACK);
        coloring.put(GameOfLife.CellStates.ALIVE, Color.WHITE);
        cellularAutomatonView = new CellularAutomatonView(gameOfLife, canvas, generationNumberLabel, coloring);
        cellularAutomatonView.randomize(); //Start with random view



        randomButton.setOnAction(this::randomizeBoard);
        nextGenerationButton.setOnAction(this::nextGeneration);
    }

    private void randomizeBoard(Event event) {
        cellularAutomatonView.randomize();
    }

    private void nextGeneration(Event event) {
        cellularAutomatonView.nextGeneration();
    }
}