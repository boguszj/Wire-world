package views.tmp;

import models.CellularAutomaton;

public interface CellularAutomatonView<T extends Enum> {
    /**
     * A single cell of automaton is drawn. In case automaton was drawn before this method should update only selected cell.
     * @param cellState state of cell that will be drown
     * @param x place of cell in the entire automaton board. Starting from <b>0</b>
     * @param y place of cell in the entire automaton board. Starting from <b>0</b>
     * @param cellSize preferred size of cell
     */
    void drawCell(T cellState, final int x, final int y, final double cellSize);

    /**
     * Draws whole CellularAutomaton
     * @param cellularAutomaton automaton to be drawn
     * @param cellSize preferred size of cell
     */
    void draw(CellularAutomaton<T> cellularAutomaton, final double cellSize);
}
