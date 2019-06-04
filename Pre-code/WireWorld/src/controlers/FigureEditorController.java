package controlers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import models.CellularAutomaton;
import models.Pattern;
import utils.Utils;
import views.CellularAutomatonView;
import views.FXCellularAutomatonView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

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

    protected double cellSize = 40.;

    protected CellularAutomatonView<T> cellularAutomatonView;
    protected CellularAutomaton<T> cellularAutomaton;

    protected Consumer<Pattern<T>> saveCallback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);

        cellularAutomatonView = new FXCellularAutomatonView<>(canvas, getColoring());
        cancelButton.setOnAction(event -> ((Stage) cancelButton.getScene().getWindow()).close());
        resetButton.setOnAction(this::createNewDrawingBoard);
        canvas.setOnMouseClicked(this::canvasClicked);
        canvas.setOnMouseDragged(this::canvasClicked);
        saveButton.setOnAction(this::saveFigure);

        createNewDrawingBoard(null);
    }

    /**
     * Set function that should be run when user saves correct pattern
     * @param saveCallback
     */
    public void setSaveCallback(Consumer<Pattern<T>> saveCallback) {
        this.saveCallback = saveCallback;
    }

    /**
     * Check if pattern entered by the user is correct
     * @return
     */
    protected boolean validateFigure() {
        if (cellularAutomaton == null) {
            new Alert(Alert.AlertType.ERROR, "No pattern drawn").showAndWait();
            return false;
        }

        if (Utils.isNullOrEmpty(figureNameTextField.getText())) {
            new Alert(Alert.AlertType.ERROR, "No figure name provided").showAndWait();
            return false;
        }

        return true;
    }

    protected void saveFigure(Event event) {
        if (!validateFigure())
            return;

        Pattern<T> pattern = new Pattern<>(figureNameTextField.getText() ,cellularAutomaton.getWidth(),
                cellularAutomaton.getHeight(), cellularAutomaton.getCells());

        ((Stage) cancelButton.getScene().getWindow()).close();
        if (saveCallback != null)
            saveCallback.accept(pattern);
    }

    /**
     * Change value of clicked cell to value selected by the user
     *
     * @param event Used for extracting mouse coordinates
     */
    protected void canvasClicked(MouseEvent event) {
        final int row = (int) (event.getY() / cellSize);
        final int column = (int) (event.getX() / cellSize);

        T selectedState = getSelectedState();

        cellularAutomaton.setCell(row, column, selectedState);
        cellularAutomatonView.drawCell(selectedState, column, row, cellSize);
    }

    protected abstract CellularAutomaton creteCellularAutomaton();

    private void createNewDrawingBoard(Event event) {
        cellularAutomaton = creteCellularAutomaton();
        cellularAutomatonView.draw(cellularAutomaton, cellSize);
    }

    protected abstract T getSelectedState();

    /**
     * Mapping of each cellular automaton state to corresponding colour should be returned
     *
     * @return mapping of each cellular automaton state to corresponding colour
     */
    protected abstract Map<T, Paint> getColoring();
}
