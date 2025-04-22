package services;


import java.sql.*;
import java.util.*;

import models.Favorite;

import database.DBConnection;

public class FavoriteService {
	
	public void addSongToFavorites(int userId, int trackId) {
        String query = "INSERT INTO playlist_songs (id_playlist, id_track) SELECT id_playlist, ? " +
                       "FROM favorites WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, trackId);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public List<Favorite> getAllFavorites() {
        List<Favorite> favorites = new ArrayList<>();
        String query = "SELECT * FROM favorites"; 

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                
                Favorite favorite = new Favorite(userId);
                favorites.add(favorite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites;
    }
	
	
    public void deleteFavorite(int id) {
        String query = "DELETE FROM favorites WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	
	
}


