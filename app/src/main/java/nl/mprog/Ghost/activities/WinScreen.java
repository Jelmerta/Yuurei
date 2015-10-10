/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import nl.mprog.Ghost.R;

/**
 * Created by Gebruiker on 10/3/2015.
 */
public class WinScreen extends AppCompatActivity{

    TextView winningMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        Intent intent = getIntent();

        String playerName = intent.getStringExtra("winning_player");
        String losingWord = intent.getStringExtra("losing_word");

        winningMessage = (TextView) findViewById(R.id.winning_message);
        winningMessage.setText(playerName + " wint!");

        //Lexicon dutchLexicon = new Lexicon("res/raw/dutch.txt");
    }
}
