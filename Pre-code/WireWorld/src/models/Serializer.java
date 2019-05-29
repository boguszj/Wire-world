package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for serializing cellular automatons to files
 */
public class Serializer {

    /**
     * Serializes CellularAutomaton to JSON and saves it to a specified file.
     * If file already exists it will be <b>overwritten</b>
     *
     * @param cellularAutomaton
     * @param file Output file
     */
    public static void serializeToJson(CellularAutomaton cellularAutomaton, File file) throws IOException {
        if (cellularAutomaton == null || file == null)
            throw new IllegalArgumentException("Arguments can't be null");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        objectMapper.writeValue(file, cellularAutomaton);
    }

    /**
     * Serializes CellularAutomaton to XML and saves it to a specified file.
     * If file already exists it will be <b>overwritten</b>
     *
     * @param cellularAutomaton
     * @param file Output file
     */
    public static void serializeToXml(CellularAutomaton cellularAutomaton, File file) throws IOException {
        if (cellularAutomaton == null || file == null)
            throw new IllegalArgumentException("Arguments can't be null");

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        xmlMapper.writeValue(file, cellularAutomaton);
    }
}
