package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import utils.Utils;

import java.io.File;
import java.io.IOException;

/**
 * Class responsible for parsing files to CellularAutomatons
 */
public class Parser {

    /**
     * Parses file into CellularAutomaton instance
     * @param file input file
     * @param automatonClass class of specific instance that should be loaded
     * @return
     */
    public static CellularAutomaton loadCellularAutomaton(File file, Class automatonClass) throws IOException {
        if (file == null)
            throw new IllegalArgumentException("File cannot be null");

        ObjectMapper objectMapper;

        String extension = Utils.extractFileExtension(file.getName());
        if (extension.equals(".json"))
            objectMapper = new ObjectMapper();
        else if (extension.equals(".xml"))
            objectMapper = new XmlMapper();
        else
            throw new IllegalArgumentException("Invalid file format");

        return (CellularAutomaton) objectMapper.readValue(file, automatonClass);
    }
}
