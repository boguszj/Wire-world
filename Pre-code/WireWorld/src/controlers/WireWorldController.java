package controlers;

import javafx.event.Event;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.CellularAutomaton;
import models.WireWorld;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.paint.Color.*;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by gridSetupController
 * It controls Game of Life tab of the main view
 */
public class WireWorldController extends CellularAutomatonController<WireWorld.CellStates> {

    private final Button powerOffButton;
    private final RadioButton emptyRadioButton;
    private final RadioButton tailRadioButton;
    private final RadioButton headRadioButton;
    private final RadioButton conductorRadioButton;


    public WireWorldController(Button powerOffButton, Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel, RadioButton emptyRadioButton, RadioButton tailRadioButton, RadioButton headRadioButton, RadioButton conductorRadioButton) {
        super(speedSlider, canvas, zoomSlider,  autoRunToggleButton,  previousGenerationButton,  nextGenerationButton,  widthSpinner,  heightSpinner,  randomButton,  emptyButton,  saveButton,  loadButton,  generationNumberLabel);

        this.powerOffButton = powerOffButton;
        this.emptyRadioButton = emptyRadioButton;
        this.headRadioButton = headRadioButton;
        this.tailRadioButton = tailRadioButton;
        this.conductorRadioButton = conductorRadioButton;

        powerOffButton.setOnAction(this::powerOff);

    }


    @Override
    protected CellularAutomaton creteCellularAutomaton() {
        return new WireWorld(widthSpinner.getValue(), heightSpinner.getValue());
    }

    @Override
    protected Map getColoring() {
        Map<WireWorld.CellStates, Paint> coloring = new HashMap<>();

        coloring.put(WireWorld.CellStates.EMPTY, Color.BLACK);
        coloring.put(WireWorld.CellStates.HEAD, BLUE);
        coloring.put(WireWorld.CellStates.TAIL, Color.RED);
        coloring.put(WireWorld.CellStates.CONDUCTOR, Color.YELLOW);

        return coloring;
    }

    @Override
    protected WireWorld.CellStates getSelectedState() {
        if (emptyRadioButton.isSelected())
            return WireWorld.CellStates.EMPTY;
        else if (headRadioButton.isSelected())
            return WireWorld.CellStates.HEAD;
        else if (tailRadioButton.isSelected())
            return WireWorld.CellStates.TAIL;
        else
            return WireWorld.CellStates.CONDUCTOR;
    }

    private void powerOff(Event event){
        ((WireWorld) cellularAutomaton).killElectrons();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

}