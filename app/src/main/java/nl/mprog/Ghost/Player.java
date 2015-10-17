/*
 * Student name: Jelmer Alphenaar
 * Student number: 10655751
 */

package nl.mprog.Ghost;

public class Player {
    private int id;
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

    public void increaseScore() {
        score++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "id = " + id + ", name = " + name + ", icon = " + icon + ", score =" + score;
    }
}
