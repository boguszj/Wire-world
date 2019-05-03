package controlers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
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

    private GameOfLife gameOfLife;
    private CellularAutomatonView cellularAutomatonView;

    public GameOfLifeController(Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton) {
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

        test();
    }

    private void test() {
//        final GraphicsContext gc = canvas.getGraphicsContext2D();
//
//
//        gc.setFill(Color.BLUE);
//        gc.fillRect(0, 0, 100, 100);
//        gc.fillRect(50, 0, 400, 400);
        gameOfLife = new GameOfLife(6, 6);
        gameOfLife.randomize();
        Map<GameOfLife.CellStates, Paint> coloring = new HashMap<>();
        coloring.put(GameOfLife.CellStates.DEAD, Color.BLACK);
        coloring.put(GameOfLife.CellStates.ALIVE, Color.WHITE);


        cellularAutomatonView = new CellularAutomatonView(gameOfLife, canvas, coloring);

        cellularAutomatonView.draw();
    }
}
