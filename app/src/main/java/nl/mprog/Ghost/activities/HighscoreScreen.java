/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import nl.mprog.Ghost.R;

/* The highscores are not yet implemented, but this should be relatively simple.
 * Go through the list of players and sort them by score, get the name and the score of the players in order
 * and show them on screen.
 */
public class HighscoreScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore_screen);

    }
}
