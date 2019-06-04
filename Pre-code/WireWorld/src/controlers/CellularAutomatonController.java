package controlers;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.CellularAutomaton;
import models.Parser;
import models.Pattern;
import models.Serializer;
import utils.Utils;
import views.CellularAutomatonView;
import views.FXCellularAutomatonView;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import static utils.Utils.myMax;
import static utils.Utils.myMin;

//TODO: States for GUI (Simulation paused, played, ...)

/**
 * Sub controller used by SetupController
 * It controls Game of Life tab of the main view
 */
public abstract class CellularAutomatonController<T extends Enum> {
    protected Canvas canvas;

    protected Slider zoomSlider;
    protected Slider speedSlider;
    protected ToggleButton autoRunToggleButton;
    protected Button previousGenerationButton;
    protected Button nextGenerationButton;

    protected Spinner<Integer> widthSpinner;
    protected Spinner<Integer> heightSpinner;
    protected Button randomButton;
    protected Button emptyButton;
    protected Button saveButton;
    protected Button loadButton;

    protected Label generationNumberLabel;

    protected CellularAutomaton cellularAutomaton;
    protected CellularAutomatonView<T> cellularAutomatonView;

    protected Button editPatternButton;
    protected Button newPatternButton;

    protected ListView patternListView;
    protected ObservableList<Pattern<T>> patterns = FXCollections.<Pattern<T>>observableArrayList();
    protected TabPane modesTabPane;

    private boolean running;
    private Thread t;
    private long delay;

    private static final double scrollRatio = 0.5;

    public CellularAutomatonController(TabPane modesTabPane, ListView patternListView, Button editPatternButton, Button newPatternButton, Slider speedSlider, Canvas canvas, Slider zoomSlider, ToggleButton autoRunToggleButton, Button previousGenerationButton, Button nextGenerationButton, Spinner widthSpinner, Spinner heightSpinner, Button randomButton, Button emptyButton, Button saveButton, Button loadButton, Label generationNumberLabel) {
        this.canvas = canvas;
        this.speedSlider = speedSlider;
        this.zoomSlider = zoomSlider;
        this.autoRunToggleButton = autoRunToggleButton;
        this.previousGenerationButton = previousGenerationButton;
        this.nextGenerationButton = nextGenerationButton;
        this.widthSpinner = widthSpinner;
        this.heightSpinner = heightSpinner;
        this.randomButton = randomButton;
        this.emptyButton = emptyButton;
        this.saveButton = saveButton;
        this.loadButton = loadButton;
        this.generationNumberLabel = generationNumberLabel;
        this.running = false;
        this.delay = (long) speedSlider.getValue();
        this.editPatternButton = editPatternButton;
        this.newPatternButton = newPatternButton;
        this.patternListView = patternListView;
        this.modesTabPane = modesTabPane;

        cellularAutomatonView = new FXCellularAutomatonView<>(canvas, getColoring());

        createThread();

        Utils.makeSpinnerUpdateValueOnFocusLost(heightSpinner);
        Utils.makeSpinnerUpdateValueOnFocusLost(widthSpinner);

        generationNumberLabel.textProperty().addListener(this::generationNumberChanged);

        zoomSlider.valueProperty().addListener(this::zoomSliderChanged);
        speedSlider.valueProperty().addListener(this::speedSliderChanged);
        nextGenerationButton.setOnAction(this::nextGeneration);
        previousGenerationButton.setOnAction(this::previousGeneration);
        autoRunToggleButton.setOnAction(this::play);
        randomButton.setOnAction(this::randomizeBoard);
        emptyButton.setOnAction(this::clearBoard);
        canvas.setOnMouseClicked(this::canvasClicked);
        canvas.setOnMouseDragged(this::canvasClicked);
        //canvas.setOnScroll(this::canvasScrolled);
        saveButton.setOnAction(this::saveCurrentGeneration);
        loadButton.setOnAction(this::lodBoard);

        newPatternButton.setOnAction(this::createFigure);
        editPatternButton.setOnAction(this::editFigure);
        patternListView.setItems(patterns);
        patternListView.setOnMouseClicked(this::patternClicked);

        editPatternButton.setDisable(true);
        patterns.addListener(new ListChangeListener<Pattern<T>>() {
            @Override
            public void onChanged(Change<? extends Pattern<T>> c) {
                editPatternButton.setDisable(patterns.size() == 0 ? true : false);
            }
        });
    }

    /**
     * Informs if controller is currently in pattern insertion mode
     * @return
     */
    protected boolean isInPatternInsertionMode() {
        return modesTabPane.getSelectionModel().getSelectedIndex() == 1;
    }

    protected void patternClicked(MouseEvent event) {
//        if (cellularAutomaton == null)
//            return;
//
//        new Alert(Alert.AlertType.INFORMATION, "Click on left upper corner of place You want to insert selected pattern").showAndWait();
    }

    protected void createFigure(Event event) {
        openFigureEditorWindow(event);
    }

    protected void editFigure(Event event) {
        openFigureEditorWindow(event).loadPattern((Pattern<T>) patternListView.getSelectionModel().getSelectedItem());
    }

    protected FigureEditorController openFigureEditorWindow(Event event) {
        try {
            FXMLLoader loader = loadEditorFXMLLoader();
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getTarget()).getScene().getWindow());
            stage.setTitle("Figure drawer");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            FigureEditorController controller = loader.getController();
            controller.setSaveCallback(pattern -> addPatternToList((Pattern<T>) pattern));

            stage.show();

            return controller;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void openFigureEditor(Event event) {

    }

    protected void addPatternToList(Pattern<T> pattern) {
        for (int i = 0; i < patterns.size(); i++) {
            if (patterns.get(i).getId() == pattern.getId()) {
                patterns.set(i, pattern);
                return;
            }
        }

        patterns.add(pattern);
    }

    /**
     * Create appropriate loader for figure editor
     * @return FXMLLoader to create window
     * @throws IOException thrown if unable to load FXML file
     */
    protected abstract FXMLLoader loadEditorFXMLLoader() throws IOException;

    protected void lodBoard(Event event) {
        if (running)
            autoRunToggleButton.fire();

        Window window = ((Node) event.getTarget()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load board");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );

        File selectedFile = fileChooser.showOpenDialog(window);

        try {
            cellularAutomaton = Parser.loadCellularAutomaton(selectedFile, getCellularAutomatonInstanceClass());

            enableButtons();
            shrinkSlider();
            generationNumberLabel.textProperty().bind(cellularAutomaton.currentGenerationProperty().asString());
            cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Unexpected error encountered when trying to read file\n\n" + e.getLocalizedMessage()).showAndWait();
        }
    }

    protected abstract Class getCellularAutomatonInstanceClass();

    protected void saveCurrentGeneration(Event event) {
        if (cellularAutomaton == null)
            throw new IllegalStateException("No cellular automaton present whe trying to save it to the file");

        boolean wasRunning = false;
        if (running) {
            autoRunToggleButton.fire();
            wasRunning = true;
        }

        Window window = ((Node) event.getTarget()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save board");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );

        File selectedFile = fileChooser.showSaveDialog(window);
        if (selectedFile != null) { // User didn't canceled
            String extension = Utils.extractFileExtension(selectedFile.getName());
            switch (extension) {
                case ".json":
                    try {
                        Serializer.serializeToJson(cellularAutomaton, selectedFile);
                    } catch (IOException e) {
                        new Alert(Alert.AlertType.ERROR, "Unexpected error encountered when trying to crate file").showAndWait();
                    }
                    break;
                case ".xml":
                    try {
                        Serializer.serializeToXml(cellularAutomaton, selectedFile);
                    } catch (IOException e) {
                        new Alert(Alert.AlertType.ERROR, "Unexpected error encountered when trying to crate file").showAndWait();
                    }
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid extension");
                    alert.setHeaderText(String.format("Invalid extension detected \"%s\"", extension));
                    alert.setContentText("Board can be saved in JSON or XML format with .json and .xml respectably.");
                    alert.showAndWait();
                    break;
            }

        }

        if (wasRunning)
            autoRunToggleButton.fire();
    }

    /**
     * Each controllers should create CellularAutomaton of type that it'll use
     *
     * @return instance of CellularAutomaton specific to given controller
     */
    protected abstract CellularAutomaton creteCellularAutomaton();

    /**
     * Mapping of each cellular automaton state to corresponding colour should be returned
     *
     * @return mapping of each cellular automaton state to corresponding colour
     */
    protected abstract Map<T, Paint> getColoring();

    /**
     * CellularAutomatonController should check which state is selected from input section and return it
     *
     * @return state selected by a user
     */
    protected abstract T getSelectedState();

    /**
     * Seat cellular automaton to random state and display it
     *
     * @param event <b>Not used</b>
     */
    protected void randomizeBoard(Event event) {
        int width = widthSpinner.getValue();
        int height = heightSpinner.getValue();

        enableButtons();
        shrinkSlider();

        if (cellularAutomaton == null
                || cellularAutomaton.getWidth() != width
                || cellularAutomaton.getHeight() != height) {
            cellularAutomaton = creteCellularAutomaton();
            generationNumberLabel.textProperty().bind(cellularAutomaton.currentGenerationProperty().asString());
        }

        cellularAutomaton.randomize();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    /**
     * Seat cellular automaton to clear state and display it
     *
     * @param event <b>Not used</b>
     */
    protected void clearBoard(Event event) {
        enableButtons();
        shrinkSlider();

        cellularAutomaton = creteCellularAutomaton();
        generationNumberLabel.textProperty().bind(cellularAutomaton.currentGenerationProperty().asString());
        cellularAutomaton.clear();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    private void canvasScrolled(ScrollEvent scrollEvent) {
        zoomSlider.setValue(zoomSlider.getValue() + scrollEvent.getDeltaY() * scrollRatio);
    }

    /**
     * Change value of clicked cell to value selected by the user
     *
     * @param event Used for extracting mouse coordinates
     */
    protected void canvasClicked(MouseEvent event) {
        final int row = (int) (event.getY() / zoomSlider.getValue());
        final int column = (int) (event.getX() / zoomSlider.getValue());

        if (isInPatternInsertionMode()) {
            Pattern<T> selectedPattern = (Pattern<T>) patternListView.getSelectionModel().getSelectedItem();
            cellularAutomaton.insertPattern(selectedPattern, column, row);
            cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
        }else {
            T selectedState = getSelectedState();

            cellularAutomaton.setCell(row, column, selectedState);
            cellularAutomatonView.drawCell(selectedState, column, row, zoomSlider.getValue());
        }
    }

    protected void shrinkSlider() {
        double Max = myMin(8000, 8000 / myMax(widthSpinner.getValue(), heightSpinner.getValue()));
        double Min = myMax(500.0 / heightSpinner.getValue(), 1150.0 / widthSpinner.getValue());
        zoomSlider.setMax(Max);
        if (Min < Max) {
            zoomSlider.setValue(Min);
        } else {
            zoomSlider.setValue(Max);
        }
    }

    protected void enableButtons() {
        nextGenerationButton.setDisable(false);
        autoRunToggleButton.setDisable(false);
        saveButton.setDisable(false);
    }

    private void createThread() {
        t = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            nextGeneration(null);
                            //System.out.println(delay);
                        }
                    });
                    try {
                        Thread.sleep(1000 / delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(true);
    }

    private void zoomSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        cellularAutomatonView.draw(cellularAutomaton, newValue.doubleValue());
    }

    private void speedSliderChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        this.delay = newValue.longValue();
    }

    protected void generationNumberChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (Integer.parseInt(newValue) <= 0) {
            previousGenerationButton.setDisable(true);
        } else {
            previousGenerationButton.setDisable(false);
        }
    }

    /**
     * Move cellular automaton to the next state and draw it
     *
     * @param event <b>Not used</b>
     */
    private void nextGeneration(Event event) {
        cellularAutomaton.nextGeneration();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    /**
     * Move cellular automaton to the previous state and draw it
     *
     * @param event <b>Not used</b>
     */
    private void previousGeneration(Event event) {
        cellularAutomaton.previousGeneration();
        cellularAutomatonView.draw(cellularAutomaton, zoomSlider.getValue());
    }

    private void play(Event event) {
        if (!running) {
            running = true;
            t.start();
        } else {
            running = false;
            try {
                t.join(); // wait for the thread to stop
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createThread();
        }
    }
}