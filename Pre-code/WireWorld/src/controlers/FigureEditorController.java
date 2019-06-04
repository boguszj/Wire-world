package controlers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import utils.Utils;
import views.CellularAutomatonView;
import views.FXCellularAutomatonView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class FigureEditorController<T extends Enum> implements Initializable {
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

    protected CellularAutomatonView<T> cellularAutomatonView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);

        cellularAutomatonView = new FXCellularAutomatonView<>(canvas, getColoring());
        cancelButton.setOnAction(event -> ((Stage) cancelButton.getScene().getWindow()).close());
    }

    protected abstract T getSelectedState();

    /**
     * Mapping of each cellular automaton state to corresponding colour should be returned
     *
     * @return mapping of each cellular automaton state to corresponding colour
     */
    protected abstract Map<T, Paint> getColoring();
}
