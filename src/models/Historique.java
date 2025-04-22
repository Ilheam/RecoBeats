package models;

public class Historique {
    private int userId;
    private Song song;
    private int playcount;

    public Historique(int userId, Song song, int playcount) {
        this.userId = userId;
        this.song = song;
        this.playcount = playcount;
    }

    public int getUserId() {
        return userId;
    }

    public Song getSong() {
        return song;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }
}


