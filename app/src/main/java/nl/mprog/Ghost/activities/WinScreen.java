/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.mprog.Ghost.Player;
import nl.mprog.Ghost.R;

/*
 * This is the class that creates the winning screen activity. It shows who won and it can start a new game with the same players/language
 */
public class WinScreen extends AppCompatActivity{

    TextView winningMessage;

    Button playAgainButton;
    ImageButton homeButton;
    ImageButton settingButton;
    ImageButton highscoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        SharedPreferences gamePrefs = this.getSharedPreferences("GamePrefsFile", MODE_PRIVATE);
        String playerListString = gamePrefs.getString("json_player_list", null);

        Type type = new TypeToken<List<Player>>() {
        }.getType();
        ArrayList<Player> playerList = new Gson().fromJson(playerListString, type);
        Intent intent = getIntent();
        Player winningPlayer = null;
        if(playerList != null) {
             winningPlayer = playerList.get(intent.getIntExtra("winning_player_id", -1));
        }
        winningMessage = (TextView) findViewById(R.id.winning_message);
        if(winningPlayer != null) {
            winningMessage.setText(winningPlayer.getName() + " wint!");
        }

        settingButton = (ImageButton) findViewById(R.id.win_setting_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(v.getContext(), SettingScreen.class);
                startActivity(settingIntent);
            }
        });

        homeButton= (ImageButton) findViewById(R.id.win_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(v.getContext(), TitleScreen.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        playAgainButton= (Button) findViewById(R.id.play_again_button);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(v.getContext(), GameScreen.class);
                gameIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(gameIntent);
            }
        });

        highscoreButton = (ImageButton) findViewById(R.id.win_highscore_button);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highscoreIntent = new Intent(v.getContext(), HighscoreScreen.class);
                startActivity(highscoreIntent);
            }
        });
    }
}
