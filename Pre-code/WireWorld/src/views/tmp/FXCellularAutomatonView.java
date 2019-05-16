package views.tmp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import models.CellularAutomaton;

import java.util.Map;

/**
 * Class responsible for drawing CellularAutomaton on JavaFX canvas with specified colours for each cell.
 */
public class FXCellularAutomatonView<T extends Enum> implements CellularAutomatonView<T> {
    private final Canvas canvas;
    private final Map<T, Paint> coloring;

    /**
     *
     * @param canvas Canvas on which automaton will be drawn
     * @param coloring map translating each cell state of CellularAutomaton for a colours that it should be drawn with.
     */
    public FXCellularAutomatonView(Canvas canvas, Map<T, Paint> coloring) {
        this.canvas = canvas;
        this.coloring = coloring;
    }

    @Override
    public void drawCell(T cellState, int x, int y, double cellSize) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(coloring.get(cellState));
        gc.fillRect(x, y, cellSize, cellSize);
    }

    @Override
    public void draw(CellularAutomaton<T> cellularAutomaton, double cellSize) {
        canvas.setHeight(cellularAutomaton.getHeight() * cellSize);
        canvas.setWidth(cellularAutomaton.getWidth() * cellSize);

        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int r = 0; r < cellularAutomaton.getHeight(); r++) {
            for (int c = 0; c < cellularAutomaton.getWidth(); c++) {
                T cell = cellularAutomaton.getCell(r, c);
                drawCell(cell, c, r, cellSize);
            }

        }
    }
}
