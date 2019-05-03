package controlers;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;

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
    }
}
