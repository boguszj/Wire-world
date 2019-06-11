package models;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static models.GameOfLife.CellStates.ALIVE;
import static models.GameOfLife.CellStates.DEAD;
import static org.junit.Assert.*;

public class SerializerTest {

    @Test
    public void serializationOfSmallGameOfLifeAutomatonToJSONIsCorrect() {
        //Given
        GameOfLife.CellStates[] cells = {ALIVE, ALIVE, DEAD, DEAD};
        CellularAutomaton cellularAutomaton = new GameOfLife(2, 2);
        cellularAutomaton.setCells(cells);
        File file = null;

        try {
            file = File.createTempFile("TMP", ".json");
        } catch (IOException e) {
            fail();
        }

        try{
            //When
            Serializer.serializeToJson(cellularAutomaton, file);

            //Then
            String fileContents = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
            assertTrue(fileContents.matches("\\{\\s*\"width\"\\s*:\\s*2,\\s*\"height\"\\s*:\\s*2,\\s*\"cells\"\\s*:\\s*\\[(\\s*\"ALIVE\"\\s*,\\s*){2}\"DEAD\"\\s*,\\s*\"DEAD\"\\s*\\]\\s*\\}"));
        } catch (IOException e) {
            fail();
        } finally {
            assertTrue(file.delete());
        }
    }
}