package nl.mprog.Ghost.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import nl.mprog.Ghost.R;
import nl.mprog.Ghost.Player;

/**
 * Created by Gebruiker on 10/9/2015.
 */
public class PlayerListView extends AppCompatActivity {

    TextView playerName;
    ListView playerNames;
    private ArrayAdapter<String> listAdapter;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = getIntent();

        final int playerChanging = intent.getIntExtra("player number", 0);
        final ArrayList<String> playerList = intent.getStringArrayListExtra("players");

        playerNames = (ListView) findViewById( R.id.mainListView );

        listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, playerList);

        //listAdapter.add("Jaap icon3");

        playerNames.setAdapter(listAdapter);

        playerNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                String selectedPlayerName = (String) (playerNames.getItemAtPosition(myItemInt));
                switch(playerChanging) {
                    case 1:
                        TitleScreen.playerName1.setText(selectedPlayerName);
                        TitleScreen.player1 = new Player(selectedPlayerName); //should get player from list of players
                        break;
                    case 2:
                        TitleScreen.playerName2.setText(selectedPlayerName);
                        TitleScreen.player1 = new Player(selectedPlayerName);
                        break;
                    default:
                        break;
                }
                finish();
            }
        });
    }
}
