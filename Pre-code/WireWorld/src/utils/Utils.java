package utils;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

public final class Utils {
    public static void makeSpinnerUpdateValueOnFocusLost(Spinner spinner) {
        SpinnerValueFactory factory = spinner.getValueFactory();
        TextFormatter formatter = new TextFormatter(factory.getConverter(), factory.getValue());
        spinner.getEditor().setTextFormatter(formatter);
        factory.valueProperty().bindBidirectional(formatter.valueProperty());
    }

    public static double myMax(double a, double b) {
        if (a > b) return a;
        return b;
    }

    public static double myMin(double a, double b) {
        if (a < b) return a;
        return b;
    }
}
