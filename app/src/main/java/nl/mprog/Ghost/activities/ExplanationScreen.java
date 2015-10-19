/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nl.mprog.Ghost.R;

/* This class creates the explanation activity which has an explanation about the game.
   The only way to go back is using the back-button on your device.
 */

public class ExplanationScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation_screen);
    }
}
