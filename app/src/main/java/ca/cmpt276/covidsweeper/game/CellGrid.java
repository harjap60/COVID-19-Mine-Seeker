package ca.cmpt276.covidsweeper.game;

import java.util.Random;

public class CellGrid {
    private Cell[][] grid;
    private int numberOfMines;
    private int minesFound;
    private static CellGrid instance;
    private CellGrid(int x, int y, int mines) {
        grid = new Cell[x][y];
        numberOfMines = mines;
        minesFound = 0;
        minesFound = 0;
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
        for (int i = 0; i < numberOfMines; i++){
            Random ran = new Random();
            int row = ran.nextInt(grid.length);
            int col = ran.nextInt(grid[0].length);

            while (grid[row][col].isMine() == true){
                row = ran.nextInt(grid.length);
                col = ran.nextInt(grid[0].length);
            }
            grid[row][col].setMine(true);
        }
    }

    public int clickedCell(int row, int col){
        // Changes cell to clicked and returns number of mines in row and column
        grid[row][col].setClicked(true);
        int leftoverMines = 0;
        for (int i = 0; i < grid.length; i++){ // length 4
            if(grid[i][col].isMine() && !grid[i][col].isClicked() && i != row){
                leftoverMines++;
            }
        }

        for (int j = 0; j < grid[0].length; j++ ){ // length 6
            if(grid[row][j].isMine() && !grid[row][j].isClicked() && j != col){
                leftoverMines++;
            }
        }
        // If cell is a mine and is not clicked yet, return -1
        // If cell is a mine and is clicked then return mines in rows and columns

        return leftoverMines;
    }

    public void resetGrid(int newX, int newY, int newMines){
        grid = new Cell[newX][newY];
        numberOfMines = newMines;
        minesFound = 0;


    }

    public void resetMinesFound(){
        minesFound = 0;
    }

    public void mineFound() {
        this.minesFound++;
    }

    public int getMinesFound() {
        return minesFound;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

}
