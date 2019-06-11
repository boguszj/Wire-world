package models;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static models.GameOfLife.CellStates.ALIVE;
import static models.GameOfLife.CellStates.DEAD;
import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void smallGameOfLifeAutomatonLoadsCorrectlyFromJSON() {
        //Given
        String fileContent = "{  \"width\" : 3,  \"height\" : 3,  \"cells\" : [ \"ALIVE\", \"ALIVE\", \"ALIVE\", \"DEAD\", \"ALIVE\", \"DEAD\", \"ALIVE\", \"ALIVE\", \"ALIVE\" ]}";
        File file = null;
        try {
            file = File.createTempFile("TMP", ".json");
        } catch (IOException e) {
            fail();
        }

        try{
            Files.write(file.toPath(), fileContent.getBytes());

            GameOfLife loadedAutomaton = (GameOfLife) Parser.loadCellularAutomaton(file, GameOfLife.class);

            assertEquals(3, loadedAutomaton.getHeight());
            assertEquals(3, loadedAutomaton.getWidth());

            GameOfLife.CellStates[] expectedCells = {ALIVE, ALIVE, ALIVE, DEAD, ALIVE, DEAD, ALIVE, ALIVE, ALIVE};
            assertArrayEquals(expectedCells, loadedAutomaton.getCells());

        } catch (IOException | ClassCastException e) {
            fail();
        } finally {
            assertTrue(file.delete());
        }
    }
}