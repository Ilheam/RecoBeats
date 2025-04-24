package services;

import java.sql.*;
import java.util.*;

import models.Favorite;
import models.Genre;
import models.Song;
import database.DBConnection;

public class FavoriteService {

	// Ajouter une chanson aux favoris
	public void addSongToFavorites(int userId, int trackId) {
		String query = "INSERT INTO favorites (id_user, id_track) VALUES (?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			statement.setInt(2, trackId);
			statement.executeUpdate();

		} catch (SQLException e) {
			// Erreur si la chanson est déjà dans les favoris (doublon)
			if (e.getSQLState().equals("23000")) { // Violation de clé primaire
				System.out.println("Cette chanson est déjà dans vos favoris.");
			} else {
				e.printStackTrace();
			}
		}
	}

	// Supprimer une chanson favorite
	public void deleteFavorite(int userId, int trackId) {
		String query = "DELETE FROM favorites WHERE id_user = ? AND id_track = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			statement.setInt(2, trackId);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Récupérer toutes les chansons favorites d'un utilisateur
	public List<Song> getFavoritesByUser(int userId) {
		List<Song> favorites = new ArrayList<>();
		String query = "SELECT t.id, t.track_name, t.artist_name, t.preview_url, t.tags, t.genre, t.year, "
				+ "t.duration_ms, t.danceability, t.energy, t.key_mode, t.loudness, t.mode, t.speechiness, "
				+ "t.acousticness, t.instrumentalness, t.liveness, t.valence, t.tempo, t.time_signature, t.img_url "
				+ "FROM favorites f " + "JOIN tracks t ON f.id_track = t.id " + "WHERE f.id_user = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Song song = new Song(rs.getInt("id"), rs.getString("track_name"), rs.getString("artist_name"),
						rs.getString("preview_url"), rs.getString("tags"), Genre.fromString(rs.getString("genre")),
						rs.getInt("year"), rs.getInt("duration_ms"), rs.getFloat("danceability"), rs.getFloat("energy"),
						rs.getInt("key_mode"), rs.getFloat("loudness"), rs.getString("mode"),
						rs.getFloat("speechiness"), rs.getFloat("acousticness"), rs.getFloat("instrumentalness"),
						rs.getFloat("liveness"), rs.getFloat("valence"), rs.getFloat("tempo"),
						rs.getInt("time_signature"), rs.getString("img_url"));
				favorites.add(song);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return favorites;
	}
}
