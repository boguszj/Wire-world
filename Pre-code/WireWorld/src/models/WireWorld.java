package models;

import sample.Cell;

import java.awt.peer.SystemTrayPeer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WireWorld extends CellularAutomaton<WireWorld.CellStates> {

    public enum CellStates {
        EMPTY,
        HEAD,
        TAIL,
        CONDUCTOR;

        public static CellStates randomState() {
            CellStates[] values = CellStates.values();
            return values[random.nextInt(values.length)];
        }

        @Override
        public String toString() {
            return Integer.toString(this.ordinal());
        }
    }

    public WireWorld(int width, int height) {
        super(width, height);

        this.cells = new CellStates[getCellCount()];
        clear();
    }

    @Override
    public CellStates[] getPossibleCellValues() {
        return CellStates.values();
    }

    @Override
    public void nextGeneration() {
        CellStates[] nextGen = new CellStates[getCellCount()];
        int[] headsCounter = countHeads();

        for (int i = 0; i < getCellCount(); i++) {
            if(cells[i] == WireWorld.CellStates.HEAD) nextGen[i] = CellStates.TAIL;
            else if(cells[i] == WireWorld.CellStates.TAIL) nextGen[i] = CellStates.CONDUCTOR;
            else if(cells[i] == CellStates.CONDUCTOR && (headsCounter[i] == 1 || headsCounter[i] == 2)) nextGen[i] = CellStates.HEAD;
            else nextGen[i] = cells[i];
        }

        this.cells = nextGen;
    }

    @Override
    public void randomize() {
        for (int i = 0; i < getCellCount(); i++) {
            cells[i] = CellStates.randomState();
        }
    }

    @Override
    protected CellStates getDefaultState() {
        return CellStates.EMPTY;
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
    private int countHeads(final int cellX, final int cellY) {
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
                if (cells[index] == CellStates.HEAD)
                    count++;
            }
        }

        return count;
    }

    /**
     * For each cell calculates how many alive neighbours it has
     * @return Array that in each fields contains count of neighbours of corresponding cell
     */
    private int[] countHeads() {
        int[] result = new int[getCellCount()];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                final int cellIndex = i*width + j;

                result[cellIndex] = countHeads(j, i);
            }
        }

        return result;
    }

    public void killElectrons() {
        for (int i = 0; i < getCellCount(); i++) {
            if (cells[i] != CellStates.EMPTY)
                cells[i] = CellStates.CONDUCTOR;
        }
    }

}
