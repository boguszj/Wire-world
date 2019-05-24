package models;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        if (Utils.extractFileExtension(file.getName()).equals(".json"))
            return loadCellularAutomatonFromJson(file, automatonClass);
        else
            throw new IllegalArgumentException("Invalid file format");
    }

    private static CellularAutomaton loadCellularAutomatonFromJson(File file, Class automatonClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return (CellularAutomaton) objectMapper.readValue(file, automatonClass);
    }
}
