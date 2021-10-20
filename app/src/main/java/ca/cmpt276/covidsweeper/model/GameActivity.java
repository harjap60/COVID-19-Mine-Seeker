package ca.cmpt276.covidsweeper.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import ca.cmpt276.covidsweeper.R;
import ca.cmpt276.covidsweeper.game.Cell;
import ca.cmpt276.covidsweeper.game.CellGrid;

public class GameActivity extends AppCompatActivity {
    private CellGrid cellGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cellGrid = CellGrid.getInstance(-1,-1, -1);

        populateButtons();
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
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                tableRow.addView(button);

                Cell[][] cells = cellGrid.getGrid();
                cells[i][b] = new Cell(i,b);
                button.setTag(cells[i][b]);///

                button.setId(i + b * cellGrid.getGrid()[0].length); // Will number the Id based on cell 0 - end
                int finalI = i;
                int finalB = b;
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Cell a = (Cell)view.getTag(); /////
//                        a.setClicked(true);
                        button.setText("" + a.xCord + "" + a.yCord);// REMOVE
                        //button.setText("" + cellGrid.clickedCell(finalI, finalB));
                        gridButtonClicked();

                        // We have i(row) and b(col) and we need to loop through all of them

                        //First change number of mines in rows
                        for(int index = 0; index < cellGrid.getGrid()[0].length; index++) {
                            if (cellGrid.getGrid()[finalI][index].isClicked()) {
                                Button btn = (Button) tableRow.getChildAt(index);
                                btn.setText("" + 69);
                            }

                        }

                        //Now change text in current col
                        for(int rowNo = 0; rowNo < cellGrid.getGrid().length; rowNo++){
                            if(cellGrid.getGrid()[rowNo][finalB].isClicked()) {
                                TableRow row = (TableRow)grid.getChildAt(rowNo); //  Here get row id depending on number of row
                                Button btn2 = (Button) row.getChildAt(finalB);
                                btn2.setText("" + 69);
                            }
                        }
                        //Add

                            a.setClicked(true);
                    }
                });
            }
        }
        // Add the mines randomly
        cellGrid.randomizeMines();
        // Add textEdit that shows 0 out of x mines found

    }

    private void setupTextEdits() {
    }

    private void gridButtonClicked() {
        //
    }
}