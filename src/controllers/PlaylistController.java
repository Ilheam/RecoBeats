package controllers;


import services.PlaylistService;
import models.Playlist;

import java.sql.SQLException;
import java.util.List;

public class PlaylistController {

    private PlaylistService playlistService;

    public PlaylistController() {
        this.playlistService = new PlaylistService();
    }

    public int createNewPlaylist(String name, int userId) {
        try {
            return playlistService.createPlaylist(name, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void addTrackToPlaylist(int playlistId, int trackId) {
        try {
            playlistService.addSongToPlaylist(playlistId, trackId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTrackFromPlaylist(int playlistId, int trackId) {
        try {
            playlistService.removeSongFromPlaylist(playlistId, trackId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Playlist> getUserPlaylists(int userId) {
        try {
            return playlistService.getUserPlaylists(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}