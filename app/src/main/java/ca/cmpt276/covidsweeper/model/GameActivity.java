/*+
This is the main activity for the actual game.
It sets up the dynamic buttons, textEdits for scans and mines, and the on click listeners for the buttons
Takes care of all functionality about the game: showing the COVID cells when clicked, updating mine and scan count
showing the dialog, and exiting to main menu
 */
package ca.cmpt276.covidsweeper.model;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
        cellGrid = CellGrid.getInstance(-1, -1, -1);
        buttons = new Button[cellGrid.getGrid().length][cellGrid.getGrid()[0].length];
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EAB8B8")));

        populateButtons();
        cellGrid.randomizeMines();
        setupTextEdits();
    }

    private void populateButtons() {
        TableLayout grid = (TableLayout) findViewById(R.id.gameGrid);

        // Adds all buttons and initializes all cells in the grid. Also setups up onclick listeners
        for (int i = 0; i < cellGrid.getGrid().length; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
            grid.addView(tableRow);

            for (int b = 0; b < cellGrid.getGrid()[0].length; b++) {
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                button.setPadding(0, 0, 0, 0);
                tableRow.addView(button);
                buttons[i][b] = button;

                Cell[][] cells = cellGrid.getGrid();
                cells[i][b] = new Cell(i, b);
                button.setTag(cells[i][b]); // Tag is used by th onclick listener to access each cell

                int finalI = i;
                int finalB = b;
                button.setOnClickListener(view -> {
                    Cell a = (Cell) view.getTag();
                    lockButtonSizes();

                    // If button is a mine and is clicked for the first time
                    if (a.isMine() && !a.isClicked()) {
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
                    else {
                        if(a.isMine() && !a.scanRevealed()){
                            ++numOfScans;
                            a.setRevealed();
                        }
                        else if(!a.isClicked()){
                            ++numOfScans;
                        }
                        a.setClicked(true);
                        if (!a.isScannable()) {
                            TextView numberOfScans = (TextView) findViewById(R.id.numOfScans);
                            numberOfScans.setText("# Scans used: " + numOfScans);
                        }
                        a.setScannable(true);

                        if (a.isMine()) {
                            button.setTypeface(null, Typeface.BOLD);
                        }
                        button.setText("" + cellGrid.clickedCell(finalI, finalB));// REMOVE

                    }

                    //First change number of mines in rows
                    for (int index = 0; index < cellGrid.getGrid()[0].length; index++) {
                        if (cellGrid.getGrid()[finalI][index].isClicked() && index != finalB && cellGrid.getGrid()[finalI][index].isScannable()) {
                            Button btn = (Button) tableRow.getChildAt(index);
                            btn.setText("" + cellGrid.clickedCell(finalI, index));
                        }
                    }

                    //Now change text in current col
                    for (int rowNo = 0; rowNo < cellGrid.getGrid().length; rowNo++) {
                        if (cellGrid.getGrid()[rowNo][finalB].isClicked() && rowNo != finalI && cellGrid.getGrid()[rowNo][finalB].isScannable()) {
                            TableRow row = (TableRow) grid.getChildAt(rowNo);
                            Button btn2 = (Button) row.getChildAt(finalB);
                            btn2.setText("" + cellGrid.clickedCell(rowNo, finalB));
                        }
                    }
                    if (cellGrid.getNumberOfMines() == cellGrid.getMinesFound()) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(GameActivity.this);
                        builder1.setMessage("Congratulations, you found all the COVID cells in " + numOfScans + " scans!");
                        builder1.setIcon(R.drawable.covid_19);
                        builder1.setCancelable(false);
                        builder1.setIcon(R.drawable.covid_19);

                        ImageView image = new ImageView(GameActivity.this);
                        image.setImageResource(R.drawable.covid_19);

                        builder1.setPositiveButton(
                                "Ok",
                                (dialog, id) -> {
                                    dialog.cancel();
                                    finishGame();
                                }).setView(image);
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                });
            }
        }
    }

    /*
    Function is called after congratulations dialog. Does a 2 second delay before going to the main menu.
     */
    private void finishGame() {
        final Handler wait = new Handler();
        wait.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1696);
    }

    /*
    Sets up the textEdits for the number of scans and number of cells/mines
     */
    private void setupTextEdits() {
        // Add textEdit that shows 0 out of x mines found
        TextView minesFound = (TextView) findViewById(R.id.minesFound);
        minesFound.setText(cellGrid.getMinesFound() + " out of " + cellGrid.getNumberOfMines() + " COVID cells found");
        // Add textEdit for number of Scans
        TextView numberOfScans = (TextView) findViewById(R.id.numOfScans);
        numberOfScans.setText("# Scans used: " + numOfScans);
    }

    /*
    Locks button sizes so they dont change after a covid cell is clicked
     */
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
}