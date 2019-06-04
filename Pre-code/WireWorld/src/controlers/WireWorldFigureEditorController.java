package controlers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.WireWorld;

import java.util.HashMap;
import java.util.Map;

public class WireWorldFigureEditorController extends FigureEditorController<WireWorld.CellStates>
{
    @FXML
    protected RadioButton headRadioButton;
    @FXML
    protected RadioButton emptyRadioButton;
    @FXML
    protected RadioButton tailRadioButton;
    @FXML
    protected RadioButton conductorRadioButton;

    @Override
    protected Map<WireWorld.CellStates, Paint> getColoring() {
        Map<WireWorld.CellStates, Paint> coloring = new HashMap<>();

        coloring.put(WireWorld.CellStates.EMPTY, Color.BLACK);
        coloring.put(WireWorld.CellStates.HEAD, Color.BLUE);
        coloring.put(WireWorld.CellStates.TAIL, Color.RED);
        coloring.put(WireWorld.CellStates.CONDUCTOR, Color.YELLOW);

        return coloring;
    }

    @Override
    protected WireWorld.CellStates getSelectedState() {
        if (headRadioButton.isSelected())
            return WireWorld.CellStates.HEAD;
        else if (emptyRadioButton.isSelected())
            return WireWorld.CellStates.EMPTY;
        else if (tailRadioButton.isSelected())
            return WireWorld.CellStates.TAIL;
        else
            return WireWorld.CellStates.CONDUCTOR;
    }
}
