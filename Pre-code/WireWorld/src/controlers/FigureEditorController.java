package controlers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import models.CellularAutomaton;
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
    protected Spinner<Integer> widthSpinner;
    @FXML
    protected Spinner<Integer> heightSpinner;

    @FXML
    protected Button resetButton;
    @FXML
    protected Button cancelButton;
    @FXML
    protected Button saveButton;

    protected CellularAutomatonView<T> cellularAutomatonView;
    protected CellularAutomaton<T> cellularAutomaton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);

        cellularAutomatonView = new FXCellularAutomatonView<>(canvas, getColoring());
        cancelButton.setOnAction(event -> ((Stage) cancelButton.getScene().getWindow()).close());
    }

    protected abstract CellularAutomaton creteCellularAutomaton();

    private void createNewDrawingBoard(Event event) {
//        cellularAutomaton = new
    }

    protected abstract T getSelectedState();

    /**
     * Mapping of each cellular automaton state to corresponding colour should be returned
     *
     * @return mapping of each cellular automaton state to corresponding colour
     */
    protected abstract Map<T, Paint> getColoring();
}
