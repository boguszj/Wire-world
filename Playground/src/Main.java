import board.*;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Board board = new Board(2, 3);

        System.out.println(board.toString());

//        Board.State s = Board.State.CONDUCTOR;
//        System.out.println(s);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            objectMapper.writeValue(System.out, board);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to serialize board");
        }
    }
}
