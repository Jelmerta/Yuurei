/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost;

import java.util.ArrayList;
import java.util.List;

/*
 * This is the class that handles the game, it keeps tracking what lexicon is being used and which players are playing.
 */
public class Game {
    public final static boolean PLAYER1 = false;
    public final static boolean PLAYER2 = true;

    public final static int DUTCH = 0;
    public final static int ENGLISH = 1;

    private Lexicon lexicon;
    private String currentPrefix = "";

    boolean turn = PLAYER1;
    boolean ended;

    List<Player> playerList;
    int playerId1;
    int playerId2;

    public Game(Lexicon lexicon, int playerId1, int playerId2) {
        this.lexicon = lexicon;
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
    }

    public String getPlayerName(boolean playerSide) {
        if(playerSide == PLAYER1) {
            return playerList.get(playerId1).getName();
        } else {
            return playerList.get(playerId2).getName();
        }
    }

    public String getPrefix() {
        return currentPrefix;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean turn() {
        return turn;
    }

    public boolean ended() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void increaseScore(int playerId) {
        Player player = playerList.get(playerId);
        player.increaseScore();
        playerList.set(playerId, player);
    }

    // With every letter that is guessed and attached to the prefix, there is a check if the game is finished. Otherwise it will continue.
    public void guess(char letter) {
        currentPrefix += letter;

        lexicon.updateFilter(currentPrefix);
        int wordsLeft = lexicon.count();
        if (wordsLeft == 0 || (currentPrefix.length() > 3 && lexicon.contains(currentPrefix))) {
            ended = true;
        } else {
            setTurn(!turn);
        }
    }

    public boolean winner() {
        return !turn;
    }

    // Everything associated with the game state is reset to the initial state so a new can begin
    public void restart() {
        lexicon.reset();
        currentPrefix = "";
        setTurn(PLAYER1);
        setEnded(false);
    }
}
