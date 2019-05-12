package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.*;

/**
 * Abstraction of cellular automaton
 *
 * @param <T> Enum type of all possible cell states
 */
public abstract class CellularAutomaton<T extends Enum> {
    protected final int width;
    protected final int height;
    protected T[] cells;

    private List<T[]> history = new ArrayList<>();
    //Number of generation that automaton is currently in. Used for lazy generation.
    private IntegerProperty currentGeneration = new SimpleIntegerProperty(0);

    protected static Random random = new Random();

    public CellularAutomaton(int width, int height) {
        this.width = width;
        this.height = height;

//        this.cells = new T[getCellCount()];
    }

    public T[] getCells() {
        return cells;
    }

    /**
     * A little workaround for getting T.values()
     *
     * @return Array of all possible values that cell can have
     */
    public abstract T[] getPossibleCellValues();

    public void setCells(T[] cells) {
        if (cells.length != width * height)
            throw new IllegalArgumentException("New cells length doesn't match size of automaton board." +
                    " Expected size: " + width * height + " Actual size: " + cells.length);

        this.cells = cells;
        clearHistory();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public T getCell(int row, int column) {
        final int index = row * width + column;
        return cells[index];
    }

    public void setCell(int row, int column, T value) {
        final int index = row * width + column;
        cells[index] = value;

        clearHistory();
    }

    /**
     * @return Number of cells in entire automaton
     */
    public int getCellCount() {
        return height * width;
    }

    /**
     * Cellular automaton moves to the next generation
     */
    public final void nextGeneration() {
        currentGeneration.add(1);
        if (currentGeneration.getValue() < history.size()) {
            cells = history.get(currentGeneration.getValue());
        } else {
            cells = generateNextGeneration();
            history.add(cells);
        }
    }

    /**
     * Cellular automaton moves to the previous generation
     */
    public final void previousGeneration() {
        currentGeneration.subtract(1);
        if (currentGeneration.getValue() < 0) {
            currentGeneration.setValue(0);
            throw new IllegalStateException("Cannot go back in history below state 0");
        }

        cells = history.get(currentGeneration.getValue());
    }

    /**
     * Cellular automaton generates cells of the next generation
     *
     * @return Cells that make up next generation
     */
    protected abstract T[] generateNextGeneration();

    /**
     * Sett all cell to default state
     */
    public void clear() {
        for (int i = 0; i < getCellCount(); i++) {
            cells[i] = getDefaultState();
        }

        clearHistory();
    }

    /**
     * Sell all cells to random values
     */
    //TODO: Try to make it not abstract
    public abstract void randomize();

    /**
     * @return Default cell state
     */
    protected abstract T getDefaultState();

    /**
     * Set current state as only one in history
     */
    private void clearHistory() {
        currentGeneration.setValue(0);
        history.clear();
        history.add(cells);
    }

    public int getCurrentGeneration() {
        return currentGeneration.getValue();
    }

    public IntegerProperty currentGenerationProperty() {
        return currentGeneration;
    }
}
