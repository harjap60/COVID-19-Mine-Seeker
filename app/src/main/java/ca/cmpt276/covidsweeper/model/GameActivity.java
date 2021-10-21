package ca.cmpt276.covidsweeper.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import ca.cmpt276.covidsweeper.R;
import ca.cmpt276.covidsweeper.game.Cell;
import ca.cmpt276.covidsweeper.game.CellGrid;

public class GameActivity extends AppCompatActivity {
    Button[][] buttons;
    private CellGrid cellGrid;
    private int numOfScans = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cellGrid = CellGrid.getInstance(-1,-1, -1);
        buttons = new Button[cellGrid.getGrid().length][cellGrid.getGrid()[0].length];

        populateButtons();
        // Add the mines randomly
        cellGrid.randomizeMines();
        setupTextEdits();

    }

    private void populateButtons() {
        TableLayout grid = (TableLayout) findViewById(R.id.gameGrid);

        // Adds all buttons and initializes all cells in the grid
        for(int i = 0; i < cellGrid.getGrid().length; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                     TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            grid.addView(tableRow);

            for(int b = 0; b < cellGrid.getGrid()[0].length; b++){
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                       TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                button.setPadding(0,0,0,0);
                tableRow.addView(button);
                buttons[i][b] = button;

                Cell[][] cells = cellGrid.getGrid();
                cells[i][b] = new Cell(i,b);
                button.setTag(cells[i][b]); // Tag is used by thr onclick listener to access cell


                int finalI = i;
                int finalB = b;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cell a = (Cell)view.getTag();
                        lockButtonSizes();

                        // If button is a mine and is clicked for the first time
                        if(a.isMine() && !a.isClicked()){
                            a.setClicked(true);
                            cellGrid.mineFound();
                            TextView minesFound = (TextView) findViewById(R.id.minesFound);
                            minesFound.setText(cellGrid.getMinesFound() + " out of " + cellGrid.getNumberOfMines() + " found");

                            int newWidth = button.getWidth();
                            int newHeight = button.getHeight();
                            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.covid_19);
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                            Resources resource = getResources();
                            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
                        }
                        // If button is not a mine or mine is already clicked
                        else{
                            a.setClicked(true);
                            ++numOfScans;
                            if(!a.isScannable()){
                                TextView numberOfScans = (TextView) findViewById(R.id.numOfScans);
                                numberOfScans.setText("# Scans used: " + numOfScans);
                            }
                            a.setScannable(true);

                            button.setText("" + cellGrid.clickedCell(finalI,finalB));// REMOVE

                        }

                        gridButtonClicked();
                        //First change number of mines in rows
                        for(int index = 0; index < cellGrid.getGrid()[0].length; index++) {
                            if (cellGrid.getGrid()[finalI][index].isClicked() && index != finalB && cellGrid.getGrid()[finalI][index].isScannable()) {
                                Button btn = (Button) tableRow.getChildAt(index);
                                btn.setText("" + cellGrid.clickedCell(finalI,index));
                            }
                        }

                        //Now change text in current col
                        for(int rowNo = 0; rowNo < cellGrid.getGrid().length; rowNo++){
                            if(cellGrid.getGrid()[rowNo][finalB].isClicked() && rowNo != finalI && cellGrid.getGrid()[rowNo][finalB].isScannable()) {
                                TableRow row = (TableRow)grid.getChildAt(rowNo);
                                Button btn2 = (Button) row.getChildAt(finalB);
                                btn2.setText("" + cellGrid.clickedCell(rowNo,finalB));
                            }
                        }
                        if(cellGrid.getNumberOfMines() == cellGrid.getMinesFound()){
                            // TODO: Create the game end animation and return to Main menu
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(GameActivity.this);
                            builder1.setMessage("Congratulations, you found all the COVID cells in " + numOfScans + " scans!");
                            builder1.setIcon(R.drawable.covid_19);
                            builder1.setCancelable(false);
                            builder1.setIcon(R.drawable.covid_19);

                            ImageView image = new ImageView(GameActivity.this);
                            image.setImageResource(R.drawable.covid_19);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    }).setView(image);
                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                            /* +
                            When the player wins, congratulate the player and return to the Main Menu.
                            4.1 When the player finds the last mine, redraw the game board showing the mine and
                            updated hidden-mine counts.
                            4.2 When the player finds all mines on the board, display a congratulations dialog.
                            4.3 The congratulations dialog must have at least one image, and some text congratulating the player.
                            4.4 When the player dismisses the dialog (taps OK, or the like), return to the Main Menu.
                            4.5 From the Main Menu, pressing the Android back button must then quit the application.
                             */
                        }
                    }
                });
            }
        }
    }

    private void setupTextEdits() {
        // Add textEdit that shows 0 out of x mines found
        TextView minesFound = (TextView) findViewById(R.id.minesFound);
        minesFound.setText(cellGrid.getMinesFound() + " out of " + cellGrid.getNumberOfMines() + " found");
        // Add textEdit for number of Scans
        TextView numberOfScans = (TextView) findViewById(R.id.numOfScans);
        numberOfScans.setText("# Scans used: " + numOfScans);
    }

    private void lockButtonSizes() {
        for (int row = 0; row < cellGrid.getGrid().length; row++) {
            for (int col = 0; col < cellGrid.getGrid()[0].length; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
    
    private void gridButtonClicked() {
        //

    }
}