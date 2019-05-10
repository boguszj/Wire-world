package models;

import models.GameOfLife;
import models.GameOfLife.CellStates;

import static org.junit.Assert.fail;

public class GameOfLifeTest {
    public static void main(String[] args) {
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
    }

    @org.junit.Test
    public void nextGeneration() {
//        fail();
    }
}
