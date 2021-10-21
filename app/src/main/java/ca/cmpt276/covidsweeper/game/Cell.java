package ca.cmpt276.covidsweeper.game;

public class Cell {
    private boolean clicked = false;
    private boolean mine = false;
    private boolean scannable = false;
    public int xCord;
    public int yCord;

    public Cell(int x, int y) {
        this.xCord = x;
        this.yCord = y;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isScannable() {
        return scannable;
    }

    public void setScannable(boolean scannable) {
        this.scannable = scannable;
    }
}
