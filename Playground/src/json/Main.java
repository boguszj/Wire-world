package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import models.Board;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Board board = new Board(2, 3);

        System.out.println("Board.toString():\n" + board.toString() + "\n");

//        Board.State s = Board.State.CONDUCTOR;
//        System.out.println(s);

        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            json = objectMapper.writeValueAsString(board);
            System.out.println("JSON:\n" + json + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to serialize board to JSON");
        }

        String xml;
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            xml = xmlMapper.writeValueAsString(board);
            System.out.println("XML:\n" + xml);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Unable to serialize board to XML");
        }
    }
}
