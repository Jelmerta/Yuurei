/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost;

/**
 * Created by Gebruiker on 10/9/2015.
 */
public class Player {
    private String name;
    private int icon;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public Player(String name, int icon) {
        this.name = name;
        this.icon = icon;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
