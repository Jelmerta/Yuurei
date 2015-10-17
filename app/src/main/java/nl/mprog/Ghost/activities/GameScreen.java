/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nl.mprog.Ghost.Game;
import nl.mprog.Ghost.Lexicon;
import nl.mprog.Ghost.Player;
import nl.mprog.Ghost.R;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Activity implements View.OnClickListener {
    public static final String GAME_PREFS = "GamePrefsFile";

    Game game;

    int languageIndex;
    int playerId1;
    int playerId2;

    ImageButton settingButton;
    ImageButton homeButton;
    ImageButton restartGameButton;

    TextView textPlayerName1;
    TextView textPlayerName2;

    TextView prefixText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        SharedPreferences gamePrefs = this.getSharedPreferences("GamePrefsFile", MODE_PRIVATE);

        Intent intent = getIntent();
        languageIndex = intent.getIntExtra("language_index", -1);
        playerId1 = intent.getIntExtra("player_id1", -1);
        playerId2 = intent.getIntExtra("player_id2", -1);
        if(playerId1 == -1) {
            languageIndex = gamePrefs.getInt("language_index", -1);
            playerId1 = gamePrefs.getInt("player_id1", -1);
            playerId2 = gamePrefs.getInt("player_id2", -1);
        }

        String playerListString = gamePrefs.getString("json_player_list", null);

        Type type = new TypeToken<List<Player>>() {
        }.getType();
        ArrayList<Player> playerList = new Gson().fromJson(playerListString, type);

        InputStream lexiconStream = getStream(languageIndex);
        Lexicon lexicon = new Lexicon(lexiconStream);

        game = new Game(lexicon, playerId1, playerId2);
        game.setPlayerList(playerList);

        prefixText = (TextView) findViewById(R.id.guessed_prefix_text);

        textPlayerName1 = (TextView) findViewById(R.id.game_player1_name);
        textPlayerName2 = (TextView) findViewById(R.id.game_player2_name);

        textPlayerName1.setText(game.getPlayerName(Game.PLAYER1));
        textPlayerName2.setText(game.getPlayerName(Game.PLAYER2));

        updatePrefixText();
        updatePlayerColors();

        ArrayList<Button> buttonList = new ArrayList<>();

        buttonList.add((Button) findViewById(R.id.keyboard_a));
        buttonList.add((Button) findViewById(R.id.keyboard_b));
        buttonList.add((Button) findViewById(R.id.keyboard_c));
        buttonList.add((Button) findViewById(R.id.keyboard_d));
        buttonList.add((Button) findViewById(R.id.keyboard_e));
        buttonList.add((Button) findViewById(R.id.keyboard_f));
        buttonList.add((Button) findViewById(R.id.keyboard_g));
        buttonList.add((Button) findViewById(R.id.keyboard_h));
        buttonList.add((Button) findViewById(R.id.keyboard_i));
        buttonList.add((Button) findViewById(R.id.keyboard_j));
        buttonList.add((Button) findViewById(R.id.keyboard_k));
        buttonList.add((Button) findViewById(R.id.keyboard_l));
        buttonList.add((Button) findViewById(R.id.keyboard_m));
        buttonList.add((Button) findViewById(R.id.keyboard_n));
        buttonList.add((Button) findViewById(R.id.keyboard_o));
        buttonList.add((Button) findViewById(R.id.keyboard_p));
        buttonList.add((Button) findViewById(R.id.keyboard_q));
        buttonList.add((Button) findViewById(R.id.keyboard_r));
        buttonList.add((Button) findViewById(R.id.keyboard_s));
        buttonList.add((Button) findViewById(R.id.keyboard_t));
        buttonList.add((Button) findViewById(R.id.keyboard_u));
        buttonList.add((Button) findViewById(R.id.keyboard_v));
        buttonList.add((Button) findViewById(R.id.keyboard_w));
        buttonList.add((Button) findViewById(R.id.keyboard_x));
        buttonList.add((Button) findViewById(R.id.keyboard_y));
        buttonList.add((Button) findViewById(R.id.keyboard_z));

        for(Button button : buttonList) {
            button.setOnClickListener(this);
        }

        settingButton = (ImageButton) findViewById(R.id.game_setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(v.getContext(), SettingScreen.class);
                startActivity(settingIntent);
            }
        });

        homeButton = (ImageButton) findViewById(R.id.game_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        restartGameButton = (ImageButton) findViewById(R.id.game_restart_button);
        restartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
            switch(v.getId()) {
                case R.id.keyboard_a:
                    game.guess('a');
                    break;
                case R.id.keyboard_b:
                    game.guess('b');
                    break;
                case R.id.keyboard_c:
                    game.guess('c');
                    break;
                case R.id.keyboard_d:
                    game.guess('d');
                    break;
                case R.id.keyboard_e:
                    game.guess('e');
                    break;
                case R.id.keyboard_f:
                    game.guess('f');
                    break;
                case R.id.keyboard_g:
                    game.guess('g');
                    break;
                case R.id.keyboard_h:
                    game.guess('h');
                    break;
                case R.id.keyboard_i:
                    game.guess('i');
                    break;
                case R.id.keyboard_j:
                    game.guess('j');
                    break;
                case R.id.keyboard_k:
                    game.guess('k');
                    break;
                case R.id.keyboard_l:
                    game.guess('l');
                    break;
                case R.id.keyboard_m:
                    game.guess('m');
                    break;
                case R.id.keyboard_n:
                    game.guess('n');
                    break;
                case R.id.keyboard_o:
                    game.guess('o');
                    break;
                case R.id.keyboard_p:
                    game.guess('p');
                    break;
                case R.id.keyboard_q:
                    game.guess('q');
                    break;
                case R.id.keyboard_r:
                    game.guess('r');
                    break;
                case R.id.keyboard_s:
                    game.guess('s');
                    break;
                case R.id.keyboard_t:
                    game.guess('t');
                    break;
                case R.id.keyboard_u:
                    game.guess('u');
                    break;
                case R.id.keyboard_v:
                    game.guess('v');
                    break;
                case R.id.keyboard_w:
                    game.guess('w');
                    break;
                case R.id.keyboard_x:
                    game.guess('x');
                    break;
                case R.id.keyboard_y:
                    game.guess('y');
                    break;
                case R.id.keyboard_z:
                    game.guess('z');
                    break;
                default:
                    break;
            }

            if(game.ended()) {
                goToWinActivity();
            } else {
                updatePlayerColors();
                updatePrefixText();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart_game:
                restartGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences settings = getSharedPreferences(GAME_PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.putInt("language_index", languageIndex);
        editor.putString("json_player_list", new Gson().toJson(game.getPlayerList()));
        editor.putInt("player_id1", playerId1);
        editor.putInt("player_id2", playerId2);

        editor.apply();
    }

    public void updatePlayerColors() {
        if(game.turn() == Game.PLAYER1) {
            textPlayerName1.setTextColor(Color.RED);
            textPlayerName2.setTextColor(Color.BLACK);
        } else {
            textPlayerName1.setTextColor(Color.BLACK);
            textPlayerName2.setTextColor(Color.RED);
        }
    }

    public InputStream getStream(int languageIndex) {
        switch(languageIndex) {
            case Game.DUTCH:
                return getResources().openRawResource(R.raw.dutch);
            case Game.ENGLISH:
                return getResources().openRawResource(R.raw.english);
            default:
                return getResources().openRawResource(R.raw.dutch);
        }
    }

    public void updatePrefixText() {
        prefixText.setText(game.getPrefix());
    }

    public void goToWinActivity() {
        Intent winIntent = new Intent(this, WinScreen.class);
        if(game.winner() == Game.PLAYER1) {
            game.increaseScore(playerId1);
            winIntent.putExtra("winning_player_id", playerId1);
        } else {
            game.increaseScore(playerId2);
            winIntent.putExtra("winning_player_id", playerId2);
        }
        winIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(winIntent);
    }

    public void restartGame() {
        game.restart();
        updatePrefixText();
        updatePlayerColors();
    }
}
