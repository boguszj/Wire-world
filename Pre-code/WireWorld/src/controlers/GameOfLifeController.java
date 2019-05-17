package controlers;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.CellularAutomaton;
import models.GameOfLife;

import java.util.HashMap;
import java.util.Map;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by SetupController
 * It controls Game of Life tab of the main view
 */
public class GameOfLifeController extends CellularAutomatonController<GameOfLife.CellStates> {

    private final RadioButton aliveRadioButton;
    private final RadioButton deadRadioButton;

    public GameOfLifeController(Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel, RadioButton aliveRadioButton, RadioButton deadRadioButton) {

        super(speedSlider, canvas, zoomSlider,  autoRunToggleButton,  previousGenerationButton,  nextGenerationButton,  widthSpinner,  heightSpinner,  randomButton,  emptyButton,  saveButton,  loadButton,  generationNumberLabel);

        this.aliveRadioButton = aliveRadioButton;
        this.deadRadioButton = deadRadioButton;
    }

    @Override
    protected GameOfLife.CellStates getSelectedState() {
        if (aliveRadioButton.isSelected())
            return GameOfLife.CellStates.ALIVE;
        else
            return GameOfLife.CellStates.DEAD;
    }

    @Override
    public Map<GameOfLife.CellStates, Paint> getColoring(){
        Map<GameOfLife.CellStates, Paint> coloring = new HashMap<>();

        coloring.put(GameOfLife.CellStates.DEAD, Color.BLACK);
        coloring.put(GameOfLife.CellStates.ALIVE, Color.WHITE);

        return coloring;
    }

    @Override
    protected CellularAutomaton creteCellularAutomaton() {
        return new GameOfLife(widthSpinner.getValue(), heightSpinner.getValue());
    }
}