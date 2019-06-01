package controlers;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.WireWorld;

import java.util.HashMap;
import java.util.Map;

public class WireWorldFigureEditorController extends FigureEditorController<WireWorld.CellStates>
{
    @Override
    protected Map<WireWorld.CellStates, Paint> getColoring() {
        Map<WireWorld.CellStates, Paint> coloring = new HashMap<>();

        coloring.put(WireWorld.CellStates.EMPTY, Color.BLACK);
        coloring.put(WireWorld.CellStates.HEAD, Color.BLUE);
        coloring.put(WireWorld.CellStates.TAIL, Color.RED);
        coloring.put(WireWorld.CellStates.CONDUCTOR, Color.YELLOW);

        return coloring;
    }
}
