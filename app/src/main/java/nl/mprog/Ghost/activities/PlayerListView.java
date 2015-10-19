/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import nl.mprog.Ghost.Game;
import nl.mprog.Ghost.Player;
import nl.mprog.Ghost.R;

/* This view is used when the player wants to change their name in the title screen.
 * The list of players is used to get the player names and show them in the list.
 * Upon selection, the player in the TitleScreen class is changed accordingly. (Right now this is done with
 * a static method, this could be changed to a sharedpreference which is loaded into the TitleScreen.)
 */
public class PlayerListView extends AppCompatActivity {

    ListView playerNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences myPrefs = this.getSharedPreferences("GamePrefsFile", MODE_PRIVATE);
        String playerListString = myPrefs.getString("json_player_list", null);
        Type type = new TypeToken<List<Player>>() {
        }.getType();
        ArrayList<Player> playerList = new Gson().fromJson(playerListString, type);
        ArrayList<String> playerNameList = new ArrayList<>();
        if(playerList != null) {
            for (Player player : playerList) {
                playerNameList.add(player.getName());
            }
        }
        Intent intent = getIntent();
        final int playerChanging = intent.getIntExtra("player number", 0);

        playerNames = (ListView) findViewById( R.id.mainListView );
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, playerNameList);
        playerNames.setAdapter(listAdapter);
        playerNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedPlayerName = (String) (playerNames.getItemAtPosition(myItemInt));
                switch(playerChanging) {
                    case 1:
                        TitleScreen.playerName1.setText(selectedPlayerName);
                        TitleScreen.editPlayer(Game.PLAYER1, myItemInt);
                        break;
                    case 2:
                        TitleScreen.playerName2.setText(selectedPlayerName);
                        TitleScreen.editPlayer(Game.PLAYER2, myItemInt);
                        break;
                    default:
                        break;
                }
                finish();
            }
        });
    }
}
