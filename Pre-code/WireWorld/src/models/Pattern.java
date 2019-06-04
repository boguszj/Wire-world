package models;

import java.util.UUID;

//Class representing rectangular pattern that could be inserted to CellularAutomaton
public class Pattern<T extends Enum> {
    protected int width;
    protected int height;

    protected String name;
    protected T[] cells;

    public UUID getId() {
        return id;
    }

    protected final UUID id;


    public Pattern(int width, int height, T[] cells) {
        this("", width, height, cells);
    }

    public Pattern(String name, int width, int height, T[] cells) {
        id = UUID.randomUUID();

        this.name = name;
        this.width = width;
        this.height = height;
        this.cells = cells;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getCell(int row, int column) {
        return cells[row * width + column];
    }

    public T[] getCells() {
        return cells;
    }

    public void setCells(T[] cells) {
        this.cells = cells;
    }
}
