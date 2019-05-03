package models;

/**
 * Abstraction of cellular automaton
 *
 * @param <T> Enum type of all possible cell states
 */
public abstract class CellularAutomaton<T extends Enum> {
    protected final int width;
    protected final int height;

    public T[] getCells() {
        return cells;
    }

    public void setCells(T[] cells) {
        if (cells.length != width * height)
            throw new IllegalArgumentException("New cells length doesn't match size of automaton board." +
                    " Expected size: " + width*height + " Actual size: " + cells.length);

        this.cells = cells;
    }

    protected T[] cells;

    public CellularAutomaton(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Cellular automaton moves to the next generation
     */
    public abstract void nextGeneration();

    /**
     * Sett all cell to default state
     */
    public abstract void clear();

    /**
     * Sell all cells to random values
     */
    //TODO: Try to make it not abstract
    public abstract void randomize();
}
