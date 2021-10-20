package ca.cmpt276.covidsweeper.game;

import java.util.LinkedList;

public class CellGrid {
    private Cell[][] grid;
    private int numberOfMines;
    private static CellGrid instance;

    private CellGrid(int x, int y, int mines) {
        grid = new Cell[x][y];
        numberOfMines = mines;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public static CellGrid getInstance(int x, int y, int mines) {
        if(instance == null) {
            instance = new CellGrid(x,y, mines);
        }
        return instance;
    }

    public void randomizeMines(){

    }

    public int clickedCell(int x, int y){
        // Changes cell to clicked and returns number of mines in row and column
        // If cell is a mine and is not clicked yet, return -1
        // If cell is a mine and is clicked then return mines in rows and columns
        return 69;
    }

    public void resetGrid(int newX, int newY){
        grid = new Cell[newX][newY];
    }

}
