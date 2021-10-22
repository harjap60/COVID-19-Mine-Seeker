/*
SettingsActivity used for selecting the ammount of mines, rows, columns for the game.
Creates buttons for the radio group and sets on-click listeners
Uses shared preferences to save the selected options, so other activities can retrieve the info
 */

package ca.cmpt276.covidsweeper.model;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ca.cmpt276.covidsweeper.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFS = "AppPrefs";
    public static final String MINE_PREFS = "MinePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EAB8B8")));

        createGridSettingsButtons();
        createMinesSettingsButtons();
    }

    private void createMinesSettingsButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_mines_ammount);
        int[] mineAmounts = getResources().getIntArray(R.array.num_mines_amount);

        for (int i = 0; i < mineAmounts.length; i++) {
            final int mineAmount = mineAmounts[i];

            RadioButton button = new RadioButton(this);
            button.setText("" + mineAmount);

            button.setOnClickListener(view -> saveMinesSelected(mineAmount));
            group.addView(button);

            // Select default button
            if (mineAmount == (getMinesSelected(this))) {
                button.setChecked(true);
            }
        }
    }

    static public int getMinesSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MINE_PREFS, MODE_PRIVATE);
        return prefs.getInt("mines", 0);
    }

    private void saveMinesSelected(int mineAmount) {
        SharedPreferences prefs = getSharedPreferences(MINE_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("mines", mineAmount);
        editor.apply();

    }

    private void createGridSettingsButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_grid_size);
        String[] boardSizes = getResources().getStringArray(R.array.num_board_size);

        for (int i = 0; i < boardSizes.length; i++) {
            final String boardSize = boardSizes[i];

            RadioButton button = new RadioButton(this);
            button.setText(boardSize);

            button.setOnClickListener(view -> saveGridSelected(boardSize));
            group.addView(button);

            // Select default button
            if (boardSize.equals(getGridSelected(this))) {
                button.setChecked(true);
            }
        }
    }

    private void saveGridSelected(String boardSize) {
        SharedPreferences prefs = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("size", boardSize);
        editor.apply();
    }

    static public String getGridSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        return prefs.getString("size", "NULL");
    }
}