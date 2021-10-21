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

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFS = "AppPrefs";
    public static final String MINE_PREFS = "MinePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        createGridSettingsButtons();
        createMinesSettingsButtons();


        String savedGrid = getGridSelected(this);
        Toast.makeText(this, "You selected " + savedGrid, Toast.LENGTH_SHORT).show();
    }

    private void createMinesSettingsButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_mines_ammount);
        int[] mineAmounts = getResources().getIntArray(R.array.num_mines_amount);

        for(int i = 0; i < mineAmounts.length; i++){
            final int mineAmount = mineAmounts[i];

            RadioButton button = new RadioButton(this);
            button.setText("" + mineAmount);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    saveMinesSelected(mineAmount);
                    Toast.makeText(getApplicationContext(),
                            "You choose "+ getMinesSelected(SettingsActivity.this),
                            Toast.LENGTH_SHORT).show();
                }
            });

            group.addView(button);

            // Select default button
            if (mineAmount == (getMinesSelected(this))){
                button.setChecked(true);
            }
        }


    }

    static public int getMinesSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MINE_PREFS, MODE_PRIVATE);
        return prefs.getInt("mines",0);
    }

    private void saveMinesSelected(int mineAmount) {
        SharedPreferences prefs = getSharedPreferences(MINE_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("mines",mineAmount);
        editor.apply();

    }

    private void createGridSettingsButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_grid_size);
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
                    Toast.makeText(getApplicationContext(),
                            "You choose "+ getGridSelected(SettingsActivity.this),
                            Toast.LENGTH_SHORT).show();
                }
            });

            group.addView(button);

            // Select default button
            if (boardSize.equals(getGridSelected(this))){
                button.setChecked(true);
            }
        }
    }

    private void saveGridSelected(String boardSize) {
        SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("size",boardSize);
        editor.apply();
    }

    static public String getGridSelected(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        return prefs.getString("size", "NULL");
    }
}