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

    /**
     * Extracts extension from file name with the leading dot
     * @param fileName
     * @return file extension with . at the beginning or <c>null</c> if no extension exists
     */
    public static String extractFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length()-1)
            return null;

        return fileName.substring(lastDotIndex);
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.trim().isEmpty())
            return false;
        return true;
    }
}
