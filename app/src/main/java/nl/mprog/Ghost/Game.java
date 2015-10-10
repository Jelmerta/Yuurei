/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost;

import java.util.List;

/**
 * Created by Gebruiker on 10/3/2015.
 */

public class Game {
    public final static boolean PLAYER1 = false;
    public final static boolean PLAYER2 = true;

    public final static int DUTCH = 0;
    public final static int ENGLISH = 1;

    private String currentPrefix = ""; // 2 classes have this now
    private Lexicon lexicon;
    //private Lexicon filteredLexicon;

    boolean turn;
    boolean ended;

    public Game(Lexicon lexicon) {
        setTurn(PLAYER1);
        this.lexicon = lexicon;
    } ///????? lexicon???

    public void guess(char letter) {
        currentPrefix += letter;
        //filteredLexicon = new Lexicon(lexicon.filter(currentPrefix)); // sounds pretty unoptimized (only do first time?)
        List words = lexicon.filter(currentPrefix);
        if(words.size() == 0 || (words.size() == 1 && words.get(0).toString().length() == currentPrefix.length())) {
            ended = true;
        } else {
            toggleTurn();
        }
    }

    public void toggleTurn() {
        turn = !turn;
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

    public boolean winner() {
        return !turn;
    }

    public void restart() {
        currentPrefix = "";
        setTurn(PLAYER1);
        setEnded(false);
    }

    public void setLexicon(Lexicon lexicon) {
        this.lexicon = lexicon;
    }

    public String getPrefix() {
        return currentPrefix;
    }

    public void setPrefix(String prefix) {
        this.currentPrefix = prefix;
    }

    //check if not a word function when clicking X
}
