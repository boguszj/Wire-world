package display;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Drawing test");
        Group root = new Group();
        Canvas canvas = new Canvas(300, 250);

        drawLayout(canvas);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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

        final int rows = 2, columns = 3;

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
