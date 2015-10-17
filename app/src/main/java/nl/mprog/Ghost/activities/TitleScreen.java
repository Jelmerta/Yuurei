/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.mprog.Ghost.Game;
import nl.mprog.Ghost.Player;
import nl.mprog.Ghost.R;

public class TitleScreen extends AppCompatActivity {
    List<Player> playerList;

    int languageAmount = 2;
    int languageIndex = 0;

    static int playerId1;
    static int playerId2;

    ImageButton languageButton;
    Button gameStartButton;
    ImageButton explanationButton;
    ImageButton settingButton;
    ImageButton highscoreButton;

    ImageButton playerIconImageButton1;
    ImageButton playerIconImageButton2;
    static TextView playerName1;
    static TextView playerName2;
    EditText newPlayerName;
    Button addPlayerButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        SharedPreferences myPrefs = this.getSharedPreferences("GamePrefsFile", MODE_PRIVATE);
        String playerListString = myPrefs.getString("json_player_list", null);

        if(playerListString == null) {
            playerList = new ArrayList<>();
            addDefaultPlayers();
        } else {
            Type type = new TypeToken<List<Player>>() {
            }.getType();
            playerList = new Gson().fromJson(playerListString, type);
            if(playerList.size() < 2) {
                addDefaultPlayers();
            }
            playerId1 = myPrefs.getInt("player_id1", 0);
            playerId2 = myPrefs.getInt("player_id2", 0);
        }

        playerIconImageButton1 = (ImageButton) findViewById(R.id.title_icon_player1);
        playerIconImageButton2 = (ImageButton) findViewById(R.id.title_icon_player2);

        playerIconImageButton1.setBackgroundResource(playerList.get(0).getIcon());
        playerIconImageButton2.setBackgroundResource(playerList.get(1).getIcon());

        playerName1 = (TextView) findViewById(R.id.player1_name);
        playerName2 = (TextView) findViewById(R.id.player2_name);
        updatePlayerTextFields();

        playerName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListIntent(v, 1);
            }
        });

        playerName2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListIntent(v, 2);
            }
        });

        newPlayerName = (EditText) findViewById(R.id.add_player_input);

        addPlayerButton = (Button) findViewById(R.id.add_player_button);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean exists = false;
                boolean added = false;
                String playerName = newPlayerName.getText().toString();
                for(Player player : playerList) {
                    if(player.getName().equals(playerName)) {
                        exists = true;
                    }
                }
                if(!exists) {
                    Player newPlayer = new Player(playerName);
                    newPlayer.setId(playerList.size());
                    playerList.add(newPlayer);
                    added = true;
                }

                newPlayerName.setText("");
                newPlayerName.setHint("New Player");
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if(added) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Name added!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        gameStartButton = (Button) findViewById(R.id.start_game_button);
        gameStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(v.getContext(), GameScreen.class);
                gameIntent.putExtra("player_id1", playerId1);
                gameIntent.putExtra("player_id2", playerId2);
                gameIntent.putExtra("language_index", languageIndex);
                gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gameIntent);
            }
        });

        explanationButton = (ImageButton) findViewById(R.id.explanation_button);
        explanationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explanationIntent = new Intent(v.getContext(), ExplanationScreen.class);
                startActivity(explanationIntent);
            }
        });

        settingButton = (ImageButton) findViewById(R.id.title_setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(v.getContext(), SettingScreen.class);
                startActivity(settingIntent);
            }
        });

        highscoreButton = (ImageButton) findViewById(R.id.title_highscore_button);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highscoreIntent = new Intent(v.getContext(), HighscoreScreen.class);
                startActivity(highscoreIntent);
            }
        });

        languageButton = (ImageButton) findViewById(R.id.language_menu);
        setBackgroundLanguageButton(languageIndex);

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(languageIndex >= languageAmount-1) {
                    languageIndex = 0;
                } else {
                    languageIndex++;
                }
                setBackgroundLanguageButton(languageIndex);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences settings = getSharedPreferences(GameScreen.GAME_PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.putInt("player_id1", playerId1);
        editor.putInt("player_id2", playerId2);
        editor.putString("json_player_list", new Gson().toJson(playerList));

        editor.apply();
    }

    public void startListIntent(View v, int playerSide) {
        Intent listIntent = new Intent(v.getContext(), PlayerListView.class);
        listIntent.putExtra("player number", playerSide);
        startActivity(listIntent);
    }

    public static void editPlayer(boolean playerSide, int playerId) {
        if(playerSide == Game.PLAYER1) {
            playerId1 = playerId;
        } else {
            System.out.println("called edit");
            playerId2 = playerId;
        }
    }

    public void updatePlayerTextFields() {
        System.out.println(playerId1 + " " + playerId2);
        playerName1.setText(playerList.get(playerId1).getName());
        System.out.println(playerList.get(playerId1).getName());
        playerName2.setText(playerList.get(playerId2).getName());
        System.out.println(playerList.get(playerId2).getName());
    }

    public void setBackgroundLanguageButton(int languageIndex) {
        switch(languageIndex) {
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

    public void addDefaultPlayers() {
        Player player1 = new Player("Karel", R.drawable.icon1);
        playerId1 = playerList.size();
        player1.setId(playerId1);
        playerList.add(player1);

        Player player2 = new Player("Piet", R.drawable.icon2);
        playerId2 = playerList.size();
        player2.setId(playerId2);
        playerList.add(player2);
    }
}
