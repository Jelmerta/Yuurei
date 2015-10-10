/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

//import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.mprog.Ghost.Game;
import nl.mprog.Ghost.Player;
import nl.mprog.Ghost.R;

public class TitleScreen extends AppCompatActivity {

    static final String PLAYERLIST= "playerList";

    int languageAmount = 2;
    int languageIndex = 0;

    ImageButton languageButton;
    Button gameStartButton;
    Button explanationButton;
    Button settingButton;
    Button highscoreButton;

    ArrayList<String> playerList;

    public static Player player1; //change next week
    public static Player player2;
    ImageButton playerIconImageButton1;
    ImageButton playerIconImageButton2;
    public static TextView playerName1;  // change this next week
    public static TextView playerName2;
    EditText newPlayerName;
    Button addPlayerButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        if (savedInstanceState != null) {
            playerList = savedInstanceState.getStringArrayList(PLAYERLIST);
        } else {
            playerList = new ArrayList<>();
            playerList.add("Karel");
            playerList.add("Piet");
        }

        //final Context context = getApplicationContext();

        //Drawable icon1 = ResourcesCompat.getDrawable(getResources(), R.drawable.icon1, null);
        //Drawable icon2 = ResourcesCompat.getDrawable(getResources(), R.drawable.icon2, null);

        player1 = new Player("Karel", R.drawable.icon1);
        player2 = new Player("Piet", R.drawable.icon2);

        playerIconImageButton1 = (ImageButton) findViewById(R.id.title_icon_player1);
        playerIconImageButton2 = (ImageButton) findViewById(R.id.title_icon_player2);

        playerIconImageButton1.setBackgroundResource(player1.getIcon());
        playerIconImageButton2.setBackgroundResource(player2.getIcon());

        playerName1 = (TextView) findViewById(R.id.player1_name);
        playerName2 = (TextView) findViewById(R.id.player2_name);

        playerName1.setText(player1.getName());
        playerName2.setText(player2.getName());

        playerName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(v.getContext(), PlayerListView.class);
                listIntent.putStringArrayListExtra("players", playerList);
                listIntent.putExtra("player number", 1);
                startActivity(listIntent);
            }
        });

        playerName2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(v.getContext(), PlayerListView.class);
                listIntent.putStringArrayListExtra("players", playerList);
                listIntent.putExtra("player number", 2);
                startActivity(listIntent);
            }
        });

        newPlayerName = (EditText) findViewById(R.id.add_player_input);
        addPlayerButton = (Button) findViewById(R.id.add_player_button);

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = newPlayerName.getText().toString();
                playerList.add(playerName);
                Player newPlayer = new Player(playerName);
                newPlayerName.setText("");
                newPlayerName.setHint("New Player");
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Name added!", Toast.LENGTH_SHORT);
                toast.show();
                //sethint / no text
            }
        });

        gameStartButton = (Button) findViewById(R.id.start_game_button);
        gameStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(v.getContext(), GameScreen.class);
                gameIntent.putExtra("player_name1", player1.getName());
                gameIntent.putExtra("player_name2", player2.getName());
                gameIntent.putExtra("languageIndex", languageIndex);
                startActivity(gameIntent);
            }
        });

        explanationButton = (Button) findViewById(R.id.explanation_button);
        explanationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explanationIntent = new Intent(v.getContext(), ExplanationScreen.class);
                startActivity(explanationIntent);
            }
        });

        settingButton = (Button) findViewById(R.id.setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(v.getContext(), SettingScreen.class);
                startActivity(settingIntent);
            }
        });

        highscoreButton = (Button) findViewById(R.id.highscore_button);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highscoreIntent = new Intent(v.getContext(), HighscoreScreen.class);
                startActivity(highscoreIntent);
            }
        });

        languageButton = (ImageButton) findViewById(R.id.language_menu);

        switch(languageIndex) {
            case Game.DUTCH:
                languageButton.setBackgroundResource(R.drawable.dutch_flag);
                break;
            case Game.ENGLISH:
                languageButton.setBackgroundResource(R.drawable.english_flag); //ResourcesCompat.getDrawable(getResources(), R.drawable.name, null);
                break;
            default:
                languageButton.setBackgroundResource(R.drawable.dutch_flag);
                break;
        }
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(languageIndex >= languageAmount-1) {
                    languageIndex = 0;
                } else {
                    languageIndex++;
                }
                switch(languageIndex) { // same code twice, refactor
                    case Game.DUTCH:
                        languageButton.setBackgroundResource(R.drawable.dutch_flag);
                        break;
                    case Game.ENGLISH:
                        languageButton.setBackgroundResource(R.drawable.english_flag);
                        break;
                    default:
                        languageButton.setBackgroundResource(R.drawable.dutch_flag);
                        break;
                }
            }
        });
    }

    //not necessary because it is a parent activity
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        for(String s : playerList) {
            System.out.println(s);
        }
        savedInstanceState.putStringArrayList(PLAYERLIST, playerList);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
