package json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MyExample {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "renault");
        objectMapper.writeValue(System.out, car);
    }


}

class Car {

    private String color;
    private String type;

    public Car(String c, String t) {
        color = c;
        type = t;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // standard getters setters
}

