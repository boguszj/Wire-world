package controlers;

import javafx.beans.value.ChangeListener;
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

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public class GameOfLifeController extends Controller{

    private final RadioButton aliveRadioButton;
    private final RadioButton deadRadioButton;


    private Map<GameOfLife.CellStates, Paint> coloring;

    public GameOfLifeController(Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel, RadioButton aliveRadioButton, RadioButton deadRadioButton) {

        super(speedSlider, canvas, zoomSlider,  autoRunToggleButton,  previousGenerationButton,  nextGenerationButton,  widthSpinner,  heightSpinner,  randomButton,  emptyButton,  saveButton,  loadButton,  generationNumberLabel);

        this.aliveRadioButton = aliveRadioButton;
        this.deadRadioButton = deadRadioButton;
        this.coloring = new HashMap<>();

        coloring.put(GameOfLife.CellStates.DEAD, Color.BLACK);
        coloring.put(GameOfLife.CellStates.ALIVE, Color.WHITE);

        super.cellularAutomatonView = new CellularAutomatonView(canvas, coloring, zoomSlider.getValue());

        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, this::canvasClicked);
        randomButton.setOnAction(this::randomizeBoard);
        emptyButton.setOnAction(this::clearBoard);

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

    private void clearBoard(Event event) {
        int width = (int) widthSpinner.getValue();
        int height = (int) heightSpinner.getValue();

        GameOfLife gameOfLife = new GameOfLife(width, height);
        gameOfLife.clear();
        cellularAutomatonView.setCellularAutomaton(gameOfLife);
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

    public Map<GameOfLife.CellStates, Paint> getColoring(){
        return coloring;
    }
}