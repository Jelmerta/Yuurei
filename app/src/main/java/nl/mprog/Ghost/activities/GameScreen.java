/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import nl.mprog.Ghost.Game;
import nl.mprog.Ghost.Lexicon;
import nl.mprog.Ghost.R;

import java.io.InputStream;

/**
 * Created by Gebruiker on 10/3/2015.
 */
public class GameScreen extends Activity {

    Game game;
    Lexicon lexicon;

    private int lexiconLanguage;
    private String playerName1;
    private String playerName2;

    TextView textPlayerName1;
    TextView textPlayerName2;

    TextView currentPrefixText;
    EditText inputField;
    ImageButton enterButton;
    Button restartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        Intent intent = getIntent();

        lexiconLanguage = intent.getIntExtra("LanguageIndex", 0);
        InputStream lexiconStream;
        lexiconStream = getResources().openRawResource(R.raw.aaa); //currently this test file is being used, because memory is not capable of loading the dutch/english files, will try to use radix trie or database instead next week if I have enough time

        /*switch(lexiconLanguage) {
            case Game.DUTCH:
                lexiconStream = getResources().openRawResource(R.raw.dutch);
                break;
            case Game.ENGLISH:
                lexiconStream = getResources().openRawResource(R.raw.english);
                break;
            default:
                lexiconStream = getResources().openRawResource(R.raw.dutch);
                break;
        }*/

        lexicon = new Lexicon(lexiconStream);

        currentPrefixText = (TextView) findViewById(R.id.guessed_prefix_text);

        if (savedInstanceState != null) {
            System.out.println("you've been here");
            playerName1 = savedInstanceState.getString("player_name1");
            playerName2 = savedInstanceState.getString("player_name2");
            lexiconLanguage = savedInstanceState.getInt("lexicon_language");
            game = new Game(lexicon); // should choose correct lexicon here
            game.setPrefix(savedInstanceState.getString("prefix"));
            game.setTurn(savedInstanceState.getBoolean("turn", false));
            game.setEnded(savedInstanceState.getBoolean("ended", false));

            currentPrefixText.setText(game.getPrefix());
            // set prefix textview

            // Restore value of members from saved state
            // get game state
        } else {
            game = new Game(lexicon);
            playerName1 = intent.getStringExtra("player_name1");
            playerName2 = intent.getStringExtra("player_name2");
        }

        textPlayerName1 = (TextView) findViewById(R.id.game_player1_name);
        textPlayerName2 = (TextView) findViewById(R.id.game_player2_name);

        textPlayerName1.setText(playerName1);
        textPlayerName2.setText(playerName2);

        if(game.turn()) {
            textPlayerName1.setTextColor(Color.BLACK);
            textPlayerName2.setTextColor(Color.RED);
        } else {
            textPlayerName1.setTextColor(Color.RED);
            textPlayerName2.setTextColor(Color.BLACK);
        }

        enterButton = (ImageButton) findViewById(R.id.game_enter_button);
        inputField = (EditText) findViewById(R.id.guessed_letter_input);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputField.getText().toString();
                if(!input.isEmpty() && input.length() == 1) { // don't make it able in the first place to enter more than 1 character
                    game.guess(input.charAt(0));
                    currentPrefixText.setText(game.getPrefix());
                    inputField.setText("");
                    if(game.ended()) {
                        Intent winIntent = new Intent(v.getContext(), WinScreen.class);
                        winIntent.putExtra("losing_word", game.getPrefix()); //also loses when no word is possible anymore, so string might not be complete word
                        if(game.winner()) {
                            winIntent.putExtra("winning_player", playerName2);
                        } else {
                            winIntent.putExtra("winning_player", playerName1);
                        }
                        startActivity(winIntent);
                    } else if(game.turn()) {
                        textPlayerName1.setTextColor(Color.BLACK);
                        textPlayerName2.setTextColor(Color.RED);
                    } else {
                        textPlayerName1.setTextColor(Color.RED);
                        textPlayerName2.setTextColor(Color.BLACK);
                    }
                }
            }
        });

        restartGame = (Button) findViewById(R.id.restart_button);

        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.restart();
                currentPrefixText.setText(game.getPrefix());
                textPlayerName1.setTextColor(Color.RED);
                textPlayerName2.setTextColor(Color.BLACK);
            }
        });
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_screen, menu);
        return true;
    }

    /* currently shows menu active over the keyboard

    @Override
    public boolean onPrepareOptionsMenu() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_change_language:
                // obviously need to do more: change item in menu / change to actual dutch/english
                InputStream lexiconStream = getResources().openRawResource(R.raw.aaa);
                lexicon = new Lexicon(lexiconStream);
                game.setLexicon(lexicon);
                return true;
            case R.id.action_restart_game:
                game.restart(); // put all this in one function - called twice
                currentPrefixText.setText(game.getPrefix());
                textPlayerName1.setTextColor(Color.RED);
                textPlayerName2.setTextColor(Color.BLACK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        System.out.println("saved");
        savedInstanceState.putBoolean("turn", game.turn());
        savedInstanceState.putBoolean("ended", game.ended());
        savedInstanceState.putInt("lexicon_language", lexiconLanguage);
        savedInstanceState.putString("player_name1", playerName1);
        savedInstanceState.putString("player_name2", playerName2);
        savedInstanceState.putString("prefix", game.getPrefix());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
