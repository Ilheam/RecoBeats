package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;

public class HistoryService {

    // Méthode pour récupérer l'historique des écoutes d'un utilisateur
    public List<String> getHistoryForUser(int userId) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT track.track_name, track.artist_name, history.play_count, history.last_played " +
                     "FROM history " +
                     "JOIN track ON history.id_track = track.id " +
                     "WHERE history.id_user = ? ORDER BY history.last_played DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String songInfo = rs.getString("track_name") + " - " + rs.getString("artist_name") +
                                  " (Played " + rs.getInt("play_count") + " times)";
                history.add(songInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}
