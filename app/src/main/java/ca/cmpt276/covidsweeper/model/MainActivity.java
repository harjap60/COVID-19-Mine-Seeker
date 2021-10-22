/*
MainActivity for showing the menu. Sets up buttons for the options screen, game screen, and help screen
Also creates a default board if none is selected, or sets up the board based on the mines, rows, columns selected in options
 */

package ca.cmpt276.covidsweeper.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import ca.cmpt276.covidsweeper.R;
import ca.cmpt276.covidsweeper.game.CellGrid;

public class MainActivity extends AppCompatActivity {
    private CellGrid gameGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gameGrid = CellGrid.getInstance(4, 6, 6); // default gameGrid

        setupHelpButton();
        setupSettingButton();
        launchGame();

    }

    private void setupHelpButton() {
        Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        });
    }

    private void setupSettingButton() {
        FloatingActionButton newBtn = (FloatingActionButton) findViewById(R.id.fab);
        newBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void setBoardAndMinesSize() {
        String boardSize = SettingsActivity.getGridSelected(this);
        int minesAmount = SettingsActivity.getMinesSelected(this);

        if (boardSize.equals("NULL")) {
            boardSize = "4 rows by 6 columns";
        }
        if (minesAmount == 0) {
            minesAmount = 6;
        }
        if (boardSize.equals("4 rows by 6 columns")) {
            gameGrid.resetGrid(4, 6, minesAmount);
        } else if (boardSize.equals("5 rows by 10 columns")) {
            gameGrid.resetGrid(5, 10, minesAmount);
        } else {
            gameGrid.resetGrid(6, 15, minesAmount);
        }
    }

    private void launchGame() {
        Button newGame = (Button) findViewById(R.id.start_game);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBoardAndMinesSize();
                gameGrid.resetMinesFound();
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}