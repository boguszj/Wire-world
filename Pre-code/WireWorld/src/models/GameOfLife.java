package models;

import java.util.Arrays;
import java.util.List;
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
        StringBuilder builder = new StringBuilder(width*height * 2);

        for (int i = 0; i < height; i++) {
            CellStates[] row = Arrays.copyOfRange(cells, i*width, i*width + width);
            List<String> rowValues = Arrays.stream(row).map(c -> c.toString()).collect(Collectors.toList());

            builder.append(String.join(" ", rowValues)).append("\n");
        }

        return builder.toString();
    }
}
