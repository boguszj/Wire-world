package models;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        } catch (IOException e) {
            fail();
        } finally {
            assertTrue(file.delete());
        }
    }
}