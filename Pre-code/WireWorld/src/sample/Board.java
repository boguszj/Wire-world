package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import static sun.swing.MenuItemLayoutHelper.max;


public class Board{


    Cell cells[][];
    int width;
    int height;
    Canvas canvas;
    GraphicsContext context;


    Board(int width, int height){
        this.width = width;
        this.height =height;
        cells = new Cell[width][height];
        this.canvas = new Canvas(width * Cell.SIZE, height * Cell.SIZE);
        this.canvas.setWidth(width * Cell.SIZE);
        this.canvas.setHeight(height * Cell.SIZE);
        this.context = this.canvas.getGraphicsContext2D();
    }

    public void drawBoard(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                Cell.drawCell(this.context, i * Cell.SIZE, j * Cell.SIZE);
            }
        }

    }

}
