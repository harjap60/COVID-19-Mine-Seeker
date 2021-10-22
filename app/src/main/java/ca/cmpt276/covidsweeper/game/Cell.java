/*
Cell is a class that will help the gamActivity run properly
Cells are used to indiicate if the buttons in the game are clicked, not clicked, scanned, not scanned, or are mines
Each button will have a tag linked to a Cell which GameActivity will use
 */

package ca.cmpt276.covidsweeper.game;

public class Cell {
    private boolean clicked = false;
    private boolean mine = false;
    private boolean scannable = false;
    private boolean scanRevealed = false;
    public int xCord;
    public int yCord;

    public Cell(int x, int y) {
        this.xCord = x;
        this.yCord = y;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean scanRevealed(){
        return scanRevealed;
    }

    public void setRevealed(){
        scanRevealed = true;
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
