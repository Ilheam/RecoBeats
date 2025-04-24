package services;

import database.DBConnection;
import models.Preference;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PreferenceService {

	public static void savePreference(Preference pref) {
		try (Connection conn = DBConnection.getConnection()) {
			String query = "INSERT INTO preferences (id_user, genre, preferred_year, duration_pref, energy, danceability, acousticness, instrumentalness, valence, speechiness) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " + "ON DUPLICATE KEY UPDATE " + "genre = VALUES(genre), "
					+ "preferred_year = VALUES(preferred_year), " + "duration_pref = VALUES(duration_pref), "
					+ "energy = VALUES(energy), " + "danceability = VALUES(danceability), "
					+ "acousticness = VALUES(acousticness), " + "instrumentalness = VALUES(instrumentalness), "
					+ "valence = VALUES(valence), " + "speechiness = VALUES(speechiness)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, pref.getUserId());
			ps.setString(2, pref.getGenre());
			ps.setInt(3, pref.getPreferredYear());
			ps.setString(4, pref.getDurationPref());
			ps.setFloat(5, pref.getEnergy());
			ps.setFloat(6, pref.getDanceability());
			ps.setFloat(7, pref.getAcousticness());
			ps.setFloat(8, pref.getInstrumentalness());
			ps.setFloat(9, pref.getValence());
			ps.setFloat(10, pref.getSpeechiness());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
