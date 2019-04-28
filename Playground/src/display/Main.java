package display;

import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Point2D;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Tests of how to display and interact wit board
 */
public class Main extends Application {
    final int rows = 2, columns = 3;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing test");
        Group root = new Group();
        Canvas canvas = new Canvas(300, 250);

        //TODO: Test drawing javafx.scene.shape on Pane
//        Pane pane = new Pane();
//        pane.getHeight();

        drawLayout(canvas);
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, this::changeColor);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void changeColor(MouseEvent event) {
        final double mouseX = event.getSceneX();
        final double mouseY = event.getSceneY();

//        String text = String.format("Mouse was clicked at: X=%f, Y=%f", mouseX, mouseY);
//        new Alert(Alert.AlertType.INFORMATION, text).show();

        Canvas canvas = (Canvas)event.getTarget();

        //Find out which rectangle was clicked
        int row = (int)(mouseY / canvas.getHeight() * this.rows);
        int column = (int)(mouseX / canvas.getWidth() * this.columns);

//        new Alert(Alert.AlertType.INFORMATION, "Row: " + row + " Column: " + column).showAndWait();

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        final double rectWidth = canvas.getWidth() / this.columns;
        final double rectHeight = canvas.getHeight() / this.rows;
        gc.setFill(Color.BLACK);

        double x = column * rectWidth, y = row * rectHeight;
        gc.fillRect(x, y, rectWidth, rectHeight);
    }

    private void drawLayout(Canvas canvas) {
        final double height = canvas.getHeight();
        final double width = canvas.getWidth();
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        //Let's try to draw 4 rectangles
//        final int numberOfRectangles = 4;

        List<Color> colors = Arrays.asList(Color.WHITE, Color.AQUA, Color.MAGENTA, Color.YELLOW);

//        gc.setFill(colors.get(1));
//        gc.fillRect(0, 0, width/2, height/2);

        final double rectWidth = canvas.getWidth() / columns;
        final double rectHeight = canvas.getHeight() / rows;

        Iterator<Color> currentColor = colors.iterator();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double x = j * rectWidth;
                double y = i * rectHeight;

                gc.setFill(currentColor.next());
                gc.fillRect(x, y, rectWidth, rectHeight);

                if (!currentColor.hasNext()) //Loop colors
                    currentColor = colors.iterator();
            }
        }
    }
}
