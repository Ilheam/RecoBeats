package services;


import java.sql.*;
import java.util.*;
import models.*;

import database.DBConnection;

public class FavoriteService {

    private Connection connection;

    public FavoriteService() {
        try {
			this.connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void addToFavorites(int userId, int trackId) throws SQLException {
        String sql = "INSERT IGNORE INTO favorites (id_user, id_track) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, trackId);
            stmt.executeUpdate();
        }
    }

    public void removeFromFavorites(int userId, int trackId) throws SQLException {
        String sql = "DELETE FROM favorites WHERE id_user = ? AND id_track = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, trackId);
            stmt.executeUpdate();
        }
    }

    public List<Integer> getFavoritesTrackIds(int userId) throws SQLException {
        List<Integer> trackIds = new ArrayList<>();
        String sql = "SELECT id_track FROM favorites WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trackIds.add(rs.getInt("id_track"));
            }
        }
        return trackIds;
    }
}