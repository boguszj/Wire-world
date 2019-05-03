package models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameOfLife extends CellularAutomaton<GameOfLife.CellStates> {

    public enum CellStates {
        DEAD,
        ALIVE;

        public static CellStates randomState() {
            CellStates[] values = CellStates.values();
            return values[random.nextInt(values.length)];
        }

        @Override
        public String toString() {
            return Integer.toString(this.ordinal());
        }
    }

    public GameOfLife(int width, int height) {
        super(width, height);

        this.cells = new CellStates[getCellCount()];
        clear();
    }

    @Override
    public void nextGeneration() {
        CellStates[] nextGen = new CellStates[getCellCount()];
        int[] aliveCount = countAliveNeighbours();

        for (int i = 0; i < getCellCount(); i++) {
            CellStates cell = cells[i];

            CellStates newState = getDefaultState();
            if (cell == CellStates.DEAD) {
                newState = aliveCount[i] == 4 ? CellStates.ALIVE : CellStates.DEAD;
            }
            else if (cell == CellStates.ALIVE) {
                Set<Integer> stillAlive = new HashSet<>(Arrays.asList(2, 3));
                newState = stillAlive.contains(aliveCount[i]) ? CellStates.ALIVE : CellStates.DEAD;
            }

            nextGen[i] = newState;
        }

        this.cells = nextGen;
    }

    @Override
    public void randomize() {
        for (CellStates cell : cells)
            cell = CellStates.randomState();
    }

    @Override
    protected CellStates getDefaultState() {
        return CellStates.DEAD;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(width * height * 2);

        for (int i = 0; i < height; i++) {
            CellStates[] row = Arrays.copyOfRange(cells, i * width, i * width + width);
            List<String> rowValues = Arrays.stream(row).map(c -> c.toString()).collect(Collectors.toList());

            builder.append(String.join(" ", rowValues)).append("\n");
        }

        return builder.toString();
    }

    /**
     * Calculates how many alive cells are around given cell
     * @param cellX
     * @param cellY
     * @return number of alive neighbours
     */
    private int countAliveNeighbours(final int cellX, final int cellY) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;

                final int neighbourX = cellX + i;
                final int neighbourY = cellY + j;

                if (neighbourX < 0 || neighbourX >= width || neighbourY < 0 || neighbourY >= height)
                    continue; //Cells out of board are DEAD

                final int index = neighbourY*width + neighbourX;
                if (cells[index] == CellStates.ALIVE)
                    count++;
            }
        }

        return count;
    }

    /**
     * For each cell calculates how many alive neighbours it has
     * @return Array that in each fields contains count of neighbours of corresponding cell
     */
    private int[] countAliveNeighbours() {
        int[] result = new int[getCellCount()];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                final int cellIndex = i*width + j;

                result[cellIndex] = countAliveNeighbours(j, i);
            }
        }

        return result;
    }
}
