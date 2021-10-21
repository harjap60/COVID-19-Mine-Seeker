package ca.cmpt276.covidsweeper.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

        gameGrid = CellGrid.getInstance(4,6,6);//6,10,15,20

        // TODO: SETTINGS button setup
        FloatingActionButton newBtn = (FloatingActionButton) findViewById(R.id.fab);
        newBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

        });



        // TODO: Setup CellGrid based on settings

        // Button to start game
        launchGame();

    }

    private void launchGame() {
        Button newGame = (Button) findViewById(R.id.start_game);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}