package ca.cmpt276.covidsweeper.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ca.cmpt276.covidsweeper.R;
import ca.cmpt276.covidsweeper.game.CellGrid;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        createSettingsButtons();

        String savedGrid = getGridSelected(this);
        Toast.makeText(this, "You selected " + savedGrid, Toast.LENGTH_SHORT);
    }

    private void createSettingsButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_install_size);
        String[] boardSizes = getResources().getStringArray(R.array.num_board_size);

        for(int i = 0; i < boardSizes.length; i++){
            final String boardSize = boardSizes[i];

            RadioButton button = new RadioButton(this);
            button.setText(boardSize);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    CellGrid cellGrid = CellGrid.getInstance(1, 1);
//                    if (button.getText() == "4 rows by 6 columns") {
//                        cellGrid.resetGrid(4, 6);
//                    } else if (button.getText() == "5 rows by 10 columns") {
//                        cellGrid.resetGrid(5, 10);
//                    } else {
//                        cellGrid.resetGrid(6, 15);
//                    }

                    saveGridSelected(boardSize);
                }
            });

            group.addView(button);

            // Select default button
            if (boardSize == getGridSelected(this)){
                button.setChecked(true);
            }
        }
    }

    private void saveGridSelected(String boardSize) {
        SharedPreferences prefs = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("size",boardSize);
        editor.apply();
    }

    static public String getGridSelected(Context context){
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return prefs.getString("size", "");
    }
}