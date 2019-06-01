package controlers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class FigureEditorController implements Initializable {
    @FXML
    protected Canvas canvas;

    @FXML
    protected TextField figureNameTextField;

    @FXML
    protected Spinner widthSpinner;
    @FXML
    protected Spinner heightSpinner;

    @FXML
    protected Button resetButton;
    @FXML
    protected Button cancelButton;
    @FXML
    protected Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
