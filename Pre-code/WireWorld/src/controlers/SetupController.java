package controlers;

import controlers.GameOfLifeController;
import controlers.WireWorldController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class SetupController extends Application implements Initializable {

    public static void main(String[] args) {
        launch(args);
    }

    //-------------------- Wireworld -------------------------
    private WireWorldController wireWorldController;

    @FXML
    private Button wireWorldPowerOffButton;

    @FXML
    private Canvas wireWorldCanvas;

    @FXML
    private Slider wireWorldZoomSlider;
    @FXML
    private Slider wireWorldSpeedSlider;
    @FXML
    private ToggleButton wireWorldAutoRunToggleButton;
    @FXML
    private Button wireWorldPreviousGenerationButton;
    @FXML
    private Button wireWorldNextGenerationButton;

    @FXML
    private Spinner<Integer> wireWorldWidthSpinner;
    @FXML
    private Spinner<Integer> wireWorldHeightSpinner;
    @FXML
    private Button wireWorldRandomButton;
    @FXML
    private Button wireWorldEmptyButton;
    @FXML
    private Button wireWorldSaveButton;
    @FXML
    private Button wireWorldLoadButton;

    @FXML
    private Label wireWorldGenerationNumberLabel;

    @FXML
    private RadioButton emptyRadioButton;
    @FXML
    private RadioButton headRadioButton;
    @FXML
    private RadioButton tailRadioButton;
    @FXML
    private RadioButton conductorRadioButton;




    //------------------ Game of Life -----------------------
    private GameOfLifeController gameOfLifeController;

    @FXML
    private Canvas gameOfLifeCanvas;

    @FXML
    private Slider gameOfLifeZoomSlider;
    @FXML
    private Slider gameOfLifeSpeedSlider;
    @FXML
    private ToggleButton gameOfLifeAutoRunToggleButton;
    @FXML
    private Button gameOfLifePreviousGenerationButton;
    @FXML
    private Button gameOfLifeNextGenerationButton;

    @FXML
    private Spinner<Integer> gameOfLifeWidthSpinner;
    @FXML
    private Spinner<Integer> gameOfLifeHeightSpinner;
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




    @FXML
    private RadioButton aliveRadioButton;
    @FXML
    private RadioButton deadRadioButton;

    //-------------------- Wireworld -------------------------

    @FXML
    private Slider speedSlider;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Pass controls to GameOfLifeController
        gameOfLifeController = new GameOfLifeController(gameOfLifeSpeedSlider, gameOfLifeCanvas, gameOfLifeZoomSlider, gameOfLifeAutoRunToggleButton, gameOfLifePreviousGenerationButton, gameOfLifeNextGenerationButton, gameOfLifeWidthSpinner, gameOfLifeHeightSpinner, gameOfLifeRandomButton, gameOfLifeEmptyButton, gameOfLifeSaveButton, gameOfLifeLoadButton, gameOfLifeGenerationNumberLabel, aliveRadioButton, deadRadioButton);
        wireWorldController = new WireWorldController(wireWorldPowerOffButton ,wireWorldSpeedSlider, wireWorldCanvas, wireWorldZoomSlider, wireWorldAutoRunToggleButton, wireWorldPreviousGenerationButton, wireWorldNextGenerationButton, wireWorldWidthSpinner, wireWorldHeightSpinner, wireWorldRandomButton, wireWorldEmptyButton, wireWorldSaveButton, wireWorldLoadButton, wireWorldGenerationNumberLabel, emptyRadioButton, tailRadioButton, headRadioButton, conductorRadioButton);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/mainWindow.fxml"));
        primaryStage.setTitle("Cellular automaton simulator");
        primaryStage.setScene(new Scene(root, 1350, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
