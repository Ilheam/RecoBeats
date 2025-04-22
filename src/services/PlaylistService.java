package services;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.*;

import database.DBConnection;

public class PlaylistService {

    private Connection connection;

    public PlaylistService()  {
        try {
			this.connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public int createPlaylist(String name, int userId) throws SQLException {
        String sql = "INSERT INTO playlist (name, id_user) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // return id_playlist
            }
        }
        return -1;
    }

    public void addSongToPlaylist(int playlistId, int trackId) throws SQLException {
        String sql = "INSERT INTO playlist_track (id_playlist, id_track) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, trackId);
            stmt.executeUpdate();
        }
    }

    public void removeSongFromPlaylist(int playlistId, int trackId) throws SQLException {
        String sql = "DELETE FROM playlist_track WHERE id_playlist = ? AND id_track = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, trackId);
            stmt.executeUpdate();
        }
    }

    public List<Playlist> getUserPlaylists(int userId) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM playlist WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Playlist p = new Playlist(rs.getInt("id_playlist"), rs.getString("name"), userId);
                playlists.add(p);
            }
        }
        return playlists;
    }
}