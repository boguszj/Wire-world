package sample;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gridSetupController implements Initializable {


    @FXML
    private TextField gridWidth; //how many cells in a single row

    @FXML
    private TextField gridHeight; //how many cells in a single row

    @FXML
    private ScrollPane gridPane;

    public double getGridWidth(){
        return gridPane.getWidth();
    }

    public int getHeight(){
        return Integer.parseInt(gridHeight.getText());
    }

    public int getWidth(){
        return Integer.parseInt(gridWidth.getText());
    }

    public void addCanvas(int width, int height) {
        Board board = new Board(width, height);
        board.drawBoard();
        gridPane.setContent(board.canvas);
    }

    public void createGrid(ActionEvent event){
        try {
        System.out.println(getWidth());
        System.out.println(getHeight());
            if (getWidth() > 0) {
                addCanvas(getWidth(), getHeight());
            } else {
                System.out.println("0 or less");
            }
        }catch(NumberFormatException e){
            System.out.println("Empty string");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
