/*
Not much here, just here to help create the help screen.
Most of the work is in the activity_help.xml(hard coded)
 */

package ca.cmpt276.covidsweeper.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.cmpt276.covidsweeper.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
}