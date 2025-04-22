package helpers;

import models.Song;


public class SongSession {
    private static SongSession instance;
    private Song selectedSong;
    private int currentUserId = -1; // -1 indique "aucun utilisateur connecté"

    private SongSession() {}

    public static SongSession getInstance() {
        if (instance == null) {
            instance = new SongSession();
        }
        return instance;
    }

    public void setSelectedSong(Song song) {
        this.selectedSong = song;
    }

    public Song getSelectedSong() {
        return this.selectedSong;
    }

    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
    }

    public int getCurrentUserId() {
        return this.currentUserId;
    }

    public boolean isUserLoggedIn() {
        return currentUserId != -1;
    }
}
