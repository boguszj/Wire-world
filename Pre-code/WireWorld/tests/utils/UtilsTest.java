package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void extractFileExtensionFromSimpleFileName() {
        final String fileName = "plik.txt";

        final String extension = Utils.extractFileExtension(fileName);

        assertEquals(".txt", extension);
    }

    @Test
    public void extractFileExtensionFromFileWithoutExtension() {
        final String fileName = "obraz do filmu";

        final String extesnion = Utils.extractFileExtension(fileName);

        assertNull(extesnion);
    }

    @Test
    public void extractFileExtensionFromFileWithDotAtTheEnd() {
        final String fileName = "zagadkowe dane.";

        final String extesnion = Utils.extractFileExtension(fileName);

        assertNull(extesnion);
    }

    @Test
    public void extractFileExtensionFromFileWithPath() {
        final String fileName = "Pliki/filmy/pobrane/widoe.mp4";

        final String extension = Utils.extractFileExtension(fileName);

        assertEquals(".mp4", extension);
    }
}