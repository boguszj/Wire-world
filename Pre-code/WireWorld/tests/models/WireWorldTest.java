package models;

import javafx.scene.control.Cell;
import models.GameOfLife.CellStates;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class WireWorldTest {
    /*public static void main(String[] args) {
        GameOfLife gameOfLife = new GameOfLife(2, 3);

        CellStates[] states = {
                CellStates.ALIVE, CellStates.ALIVE,
                CellStates.DEAD, CellStates.DEAD,
                CellStates.ALIVE, CellStates.ALIVE
        };
        gameOfLife.setCells(states);

        System.out.println(gameOfLife);

        gameOfLife.nextGeneration();

        System.out.println(gameOfLife);
    }*/

    @Test
    public void nextGeneration() {
        WireWorld wireWorld = new WireWorld(3, 3);
        WireWorld.CellStates[] states = {WireWorld.CellStates.TAIL, WireWorld.CellStates.HEAD, WireWorld.CellStates.HEAD, WireWorld.CellStates.EMPTY, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.HEAD, WireWorld.CellStates.TAIL, WireWorld.CellStates.EMPTY, WireWorld.CellStates.TAIL};
        wireWorld.setCells(states);
        wireWorld.nextGeneration();
        WireWorld.CellStates[] result = {WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.TAIL, WireWorld.CellStates.TAIL, WireWorld.CellStates.EMPTY, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.TAIL, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.EMPTY, WireWorld.CellStates.CONDUCTOR};
        assertArrayEquals(result, wireWorld.getCells());
    }

    @Test
    public void insertPattern() {
        WireWorld wireWorld= new WireWorld(5, 5);
        WireWorld.CellStates[] states = {
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY
        };
        wireWorld.setCells(states);

        WireWorld.CellStates[] patternStates = {
                WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR,
                WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR
        };

        Pattern<WireWorld.CellStates> pattern = new Pattern<>(3, 2, patternStates);

        //When
        wireWorld.insertPattern(pattern, 0, 0);

        //Then
        WireWorld.CellStates[] expectedStates = {
                WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.CONDUCTOR, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY,
                WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY, WireWorld.CellStates.EMPTY
        };

        assertArrayEquals(expectedStates, wireWorld.getCells());
    }
}
