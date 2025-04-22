package services;

import java.sql.*;
import java.util.*;

import database.DBConnection;
import models.Historique;
import models.Song;

public class HistoriqueService {
    private SongService songService = new SongService();

    public void incrementPlayCount(int userId, int trackId) {
        String query = "INSERT INTO historique (id_user, id_track, playcount) VALUES (?, ?, 1) " +
                       "ON DUPLICATE KEY UPDATE playcount = playcount + 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, trackId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Historique> getHistoriqueForUser(int userId) {
        List<Historique> historiqueList = new ArrayList<>();
        String query = "SELECT id_track, playcount FROM historique WHERE id_user = ? ORDER BY playcount DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int trackId = rs.getInt("id_track");
                int playcount = rs.getInt("playcount");
                Song song = songService.getSongById(trackId);

                if (song != null) {
                    historiqueList.add(new Historique(userId, song, playcount));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historiqueList;
    }
}



