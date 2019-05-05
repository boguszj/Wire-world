package controlers;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
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

import static javafx.scene.paint.Color.*;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public class WireWorldController extends Controller {

    @FXML
    private final RadioButton emptyRadioButton;
    @FXML
    private final RadioButton tailRadioButton;
    @FXML
    private final RadioButton headRadioButton;
    @FXML
    private final RadioButton conductorRadioButton;

    private Map<WireWorld.CellStates, Paint> coloring;


    public WireWorldController(Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel, RadioButton emptyRadioButton, RadioButton tailRadioButton, RadioButton headRadioButton, RadioButton conductorRadioButton) {
        super(speedSlider, canvas, zoomSlider,  autoRunToggleButton,  previousGenerationButton,  nextGenerationButton,  widthSpinner,  heightSpinner,  randomButton,  emptyButton,  saveButton,  loadButton,  generationNumberLabel);

        this.emptyRadioButton = emptyRadioButton;
        this.headRadioButton = headRadioButton;
        this.tailRadioButton = tailRadioButton;
        this.conductorRadioButton = conductorRadioButton;
        this.coloring = new HashMap<>();

        coloring.put(WireWorld.CellStates.EMPTY, Color.BLACK);
        coloring.put(WireWorld.CellStates.HEAD, BLUE);
        coloring.put(WireWorld.CellStates.TAIL, Color.RED);
        coloring.put(WireWorld.CellStates.CONDUCTOR, Color.YELLOW);

        super.cellularAutomatonView = new CellularAutomatonView(canvas, coloring, zoomSlider.getValue());
        cellularAutomatonView.generationNumberProperty().addListener(this::generationNumberChanged);
        generationNumberLabel.textProperty().bind(cellularAutomatonView.generationNumberProperty().asString());

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
            WireWorld wireWorld = new WireWorld(width, height);
            cellularAutomatonView.setCellularAutomaton(wireWorld);
        }

        cellularAutomatonView.randomize();
    }

    private void clearBoard(Event event) {
        int width = (int) widthSpinner.getValue();
        int height = (int) heightSpinner.getValue();

        WireWorld wireWorld = new WireWorld(width, height);
        wireWorld.clear();
        cellularAutomatonView.setCellularAutomaton(wireWorld);
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

}