package models;

import java.util.UUID;

//Class representing rectangular pattern that could be inserted to CellularAutomaton
public class Pattern<T extends Enum> {
    protected final int width;
    protected final int height;

    protected String name;
    protected T[] cells;

    public UUID getId() {
        return id;
    }

    protected UUID id;

    public Pattern(int width, int height, T[] cells) {
        id = UUID.randomUUID();

        this.width = width;
        this.height = height;
        this.cells = cells;
    }

    public int getWidth() {
        return width;
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
