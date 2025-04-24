<<<<<<< HEAD
package services;

import models.Genre;
import models.Song;
import helpers.CosineSimilarity;
import database.DBConnection;
import java.sql.*;
import java.util.*;

public class RecommendationService {

	private Connection connection;

	public RecommendationService() {
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Song> getRecommendedSongs(int trackId, int userId) {
		Song clickedSong = getSongById(trackId);
		List<Song> allSongs = getAllSongs();
		List<Song> recommendedSongs = new ArrayList<>();

		// Récupérer les préférences de l'utilisateur
		Map<String, Double> userPreferences = getUserPreferences(userId);
		double[] userProfile = generateUserProfile(clickedSong, userPreferences);

		// Comparer le profil enrichi avec tous les morceaux disponibles
		for (Song song : allSongs) {
			if (song.getTrack_id() != trackId) {
				double similarity = CosineSimilarity.calculate(clickedSong, song);
				double weightedSimilarity = (0.5 * similarity) + (0.3 * similarity) + (0.2 * similarity);
				if (weightedSimilarity > 0.5) {
					recommendedSongs.add(song);
				}
			}
		}

		return recommendedSongs;
	}

	private Song getSongById(int trackId) {
		String query = "SELECT * FROM songs WHERE track_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, trackId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return new Song(rs.getInt("track_id"), rs.getString("track_name"), rs.getString("artist_name"),
						rs.getString("preview_url"), rs.getString("tags"), Genre.valueOf(rs.getString("genre")),
						rs.getInt("year"), rs.getInt("duration_ms"), rs.getFloat("danceability"), rs.getFloat("energy"),
						rs.getInt("key_mode"), rs.getFloat("loudness"), rs.getString("mode"),
						rs.getFloat("speechiness"), rs.getFloat("acousticness"), rs.getFloat("instrumentalness"),
						rs.getFloat("liveness"), rs.getFloat("valence"), rs.getFloat("tempo"),
						rs.getInt("time_signature"), rs.getString("img_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Song> getAllSongs() {
		List<Song> songs = new ArrayList<>();
		String query = "SELECT * FROM songs";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				songs.add(new Song(rs.getInt("track_id"), rs.getString("track_name"), rs.getString("artist_name"),
						rs.getString("preview_url"), rs.getString("tags"), Genre.valueOf(rs.getString("genre")),
						rs.getInt("year"), rs.getInt("duration_ms"), rs.getFloat("danceability"), rs.getFloat("energy"),
						rs.getInt("key_mode"), rs.getFloat("loudness"), rs.getString("mode"),
						rs.getFloat("speechiness"), rs.getFloat("acousticness"), rs.getFloat("instrumentalness"),
						rs.getFloat("liveness"), rs.getFloat("valence"), rs.getFloat("tempo"),
						rs.getInt("time_signature"), rs.getString("img_url")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return songs;
	}

	private Map<String, Double> getUserPreferences(int userId) {
		Map<String, Double> preferences = new HashMap<>();
		String query = "SELECT * FROM user_preferences WHERE user_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				preferences.put(rs.getString("genre"), rs.getDouble("preference_value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preferences;
	}

	private double[] generateUserProfile(Song clickedSong, Map<String, Double> userPreferences) {
		// Combinaison des préférences de l'utilisateur et des caractéristiques du morceau
		double[] userProfile = new double[10];
		userProfile[0] = userPreferences.getOrDefault(clickedSong.getGenre(), 0.0);
		userProfile[1] = clickedSong.getDanceability();
		userProfile[2] = clickedSong.getEnergy();
		userProfile[3] = clickedSong.getLoudness();
		userProfile[4] = clickedSong.getSpeechiness();
		userProfile[5] = clickedSong.getAcousticness();
		userProfile[6] = clickedSong.getInstrumentalness();
		userProfile[7] = clickedSong.getLiveness();
		userProfile[8] = clickedSong.getTempo();
		userProfile[9] = clickedSong.getDuration_ms();
		return userProfile;
	}
=======

public class RecommendationService {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
