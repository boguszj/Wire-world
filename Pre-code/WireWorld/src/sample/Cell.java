package sample;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Cell {

    int type;
    int x;
    int y;
    public static final int SIZE = 50;//new gridSetupController().getGridWidth() / new gridSetupController().getWidth();

    Cell(int type, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public static void drawCell(GraphicsContext gc, int x, int y){
        gc.strokeRect(x, y, SIZE, SIZE);
    }

}
