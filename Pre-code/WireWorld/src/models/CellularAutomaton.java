package models;

import java.util.Random;

/**
 * Abstraction of cellular automaton
 *
 * @param <T> Enum type of all possible cell states
 */
public abstract class CellularAutomaton<T extends Enum> {
    protected final int width;
    protected final int height;
    protected T[] cells;

    protected static Random random = new Random();

    public CellularAutomaton(int width, int height) {
        this.width = width;
        this.height = height;

//        this.cells = new T[getCellCount()];
    }

    public T[] getCells() {
        return cells;
    }

    public void setCells(T[] cells) {
        if (cells.length != width * height)
            throw new IllegalArgumentException("New cells length doesn't match size of automaton board." +
                    " Expected size: " + width*height + " Actual size: " + cells.length);

        this.cells = cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     *
     * @return Number of cells in entire automaton
     */
    public int getCellCount() {
        return height * width;
    }

    /**
     * Cellular automaton moves to the next generation
     */
    public abstract void nextGeneration();

    /**
     * Sett all cell to default state
     */
    public void clear() {
        for (int i = 0; i < getCellCount(); i++) {
            cells[i] = getDefaultState();
        }
    }

    /**
     * Sell all cells to random values
     */
    //TODO: Try to make it not abstract
    public abstract void randomize();

    /**
     *
     * @return Default cell state
     */
    protected abstract T getDefaultState();
}
