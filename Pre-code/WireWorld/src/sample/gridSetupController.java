package sample;

import controlers.GameOfLifeController;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class gridSetupController implements Initializable {

    //-------------------- Wireworld -------------------------
    @FXML
    private TextField gridWidth; //how many cells in a single row

    @FXML
    private TextField gridHeight; //how many cells in a single row

    @FXML
    private ScrollPane gridPane;

    //------------------ Game of Life -----------------------
    private GameOfLifeController gameOfLifeController;

    @FXML
    private Canvas gameOfLifeCanvas;

    @FXML
    private Slider gameOfLifeZoomSlider;
    @FXML
    private ToggleButton gameOfLifeAutoRunToggleButton;
    @FXML
    private Button gameOfLifePreviousGenerationButton;
    @FXML
    private Button gameOfLifeNextGenerationButton;

    @FXML
    private Spinner gameOfLifeWidthSpinner;
    @FXML
    private Spinner gameOfLifeHeightSpinner;
    @FXML
    private Button gameOfLifeRandomButton;
    @FXML
    private Button gameOfLifeEmptyButton;
    @FXML
    private Button gameOfLifeSaveButton;
    @FXML
    private Button gameOfLifeLoadButton;

    @FXML
    private Label gameOfLifeGenerationNumberLabel;

    //-------------------- Wireworld -------------------------

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
        //Prevent user from entering non numeric size
        UnaryOperator<TextFormatter.Change> positiveIntegerFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        gridHeight.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, positiveIntegerFilter));
        gridWidth.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, positiveIntegerFilter));

        //Pass controls to GameOfLifeController
        gameOfLifeController = new GameOfLifeController(gameOfLifeCanvas, gameOfLifeZoomSlider, gameOfLifeAutoRunToggleButton, gameOfLifePreviousGenerationButton, gameOfLifeNextGenerationButton, gameOfLifeWidthSpinner, gameOfLifeHeightSpinner, gameOfLifeRandomButton, gameOfLifeEmptyButton, gameOfLifeSaveButton, gameOfLifeLoadButton, gameOfLifeGenerationNumberLabel);
    }

}
