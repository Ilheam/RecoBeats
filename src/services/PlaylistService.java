package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.*;

import database.DBConnection;

public class PlaylistService {

    
    public void addPlaylist(Playlist playlist) {
        String query = "INSERT INTO playlists (name, user_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, playlist.getName());
            statement.setInt(2, playlist.getUser_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public List<Playlist> getPlaylistsByUser(int userId) {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlists WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Playlist playlist = new Playlist(
                    resultSet.getInt("id_playlist"),
                    resultSet.getString("name"),
                    resultSet.getInt("user_id")
                );
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }
    
    
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String query = "SELECT * FROM playlist";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Playlist playlist = new Playlist(
                        resultSet.getInt("id_playlist"),
                        resultSet.getString("nom"),
                        resultSet.getInt("id_user")
                );
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }
    
    
    
    public void deletePlaylist(int id_playlist) {
        String query = "DELETE FROM playlist WHERE id_playlist = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id_playlist);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
