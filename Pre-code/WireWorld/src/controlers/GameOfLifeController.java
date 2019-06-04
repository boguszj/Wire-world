package controlers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.CellularAutomaton;
import models.GameOfLife;
import models.Pattern;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static models.GameOfLife.CellStates.ALIVE;
import static models.GameOfLife.CellStates.DEAD;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by SetupController
 * It controls Game of Life tab of the main view
 */
public class GameOfLifeController extends CellularAutomatonController<GameOfLife.CellStates> {

    private final RadioButton aliveRadioButton;
    private final RadioButton deadRadioButton;

    public GameOfLifeController(ListView patternListView, Button editPatternButton, Button newPatternButton, Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel, RadioButton aliveRadioButton, RadioButton deadRadioButton) {

        super(patternListView, editPatternButton, newPatternButton ,speedSlider, canvas, zoomSlider,  autoRunToggleButton,  previousGenerationButton,  nextGenerationButton,  widthSpinner,  heightSpinner,  randomButton,  emptyButton,  saveButton,  loadButton,  generationNumberLabel);

        this.aliveRadioButton = aliveRadioButton;
        this.deadRadioButton = deadRadioButton;

        loadInitialPatterns();
    }

    private void loadInitialPatterns() {
        Pattern<GameOfLife.CellStates> blinker = new Pattern<>("Blinker", 3, 3,
                new GameOfLife.CellStates[] {DEAD, ALIVE, DEAD,
                                             DEAD, ALIVE, DEAD,
                                             DEAD, ALIVE, DEAD});
        patterns.add(blinker);

        Pattern<GameOfLife.CellStates> block = new Pattern<>("Block", 2, 2,
                new GameOfLife.CellStates[] {ALIVE, ALIVE,
                                             ALIVE, ALIVE});
        patterns.add(block);
    }

    @Override
    protected GameOfLife.CellStates getSelectedState() {
        if (aliveRadioButton.isSelected())
            return ALIVE;
        else
            return DEAD;
    }

    @Override
    public Map<GameOfLife.CellStates, Paint> getColoring(){
        Map<GameOfLife.CellStates, Paint> coloring = new HashMap<>();

        coloring.put(DEAD, Color.BLACK);
        coloring.put(ALIVE, Color.WHITE);

        return coloring;
    }

    @Override
    protected FXMLLoader loadEditorFXMLLoader() throws IOException {
        return new FXMLLoader(getClass().getResource("../views/GameOfLifeFigureDrawer.fxml"));
    }

    @Override
    protected Class getCellularAutomatonInstanceClass() {
        return GameOfLife.class;
    }

    @Override
    protected CellularAutomaton creteCellularAutomaton() {
        return new GameOfLife(widthSpinner.getValue(), heightSpinner.getValue());
    }
}