package controlers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.GameOfLife;

import java.util.HashMap;
import java.util.Map;

public class GameOfLifeFigureEditorController extends FigureEditorController<GameOfLife.CellStates> {

    @FXML
    protected RadioButton aliveRadioButton;
    @FXML
    protected RadioButton deadRadioButton;

    @Override
    protected Map getColoring() {
        Map<GameOfLife.CellStates, Paint> coloring = new HashMap<>();

        coloring.put(GameOfLife.CellStates.DEAD, Color.BLACK);
        coloring.put(GameOfLife.CellStates.ALIVE, Color.WHITE);

        return coloring;
    }

    @Override
    protected GameOfLife.CellStates getSelectedState() {
        return aliveRadioButton.isSelected() ? GameOfLife.CellStates.ALIVE : GameOfLife.CellStates.DEAD;
    }
}
