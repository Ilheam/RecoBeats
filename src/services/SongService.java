package services;

import java.sql.*;
import java.util.*;

import models.Genre;
import models.Song;

import database.DBConnection;

public class SongService {

	private List<Song> songs;

	// Récupère toutes les chansons
	public List<Song> getAllSongs() {
		songs = new ArrayList<>();
		String query = "SELECT * FROM tracks LIMIT 10";

		try (Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			while (resultSet.next()) {
				String genreString = resultSet.getString("genre");
				Genre genre = getSafeGenre(genreString);

				Song song = new Song(resultSet.getInt("id"), resultSet.getString("track_name"),
						resultSet.getString("artist_name"), resultSet.getString("preview_url"),
						resultSet.getString("tags"), genre, resultSet.getInt("year"), resultSet.getInt("duration_ms"),
						resultSet.getFloat("danceability"), resultSet.getFloat("energy"), resultSet.getInt("key_mode"),
						resultSet.getFloat("loudness"), resultSet.getString("mode"), resultSet.getFloat("speechiness"),
						resultSet.getFloat("acousticness"), resultSet.getFloat("instrumentalness"),
						resultSet.getFloat("liveness"), resultSet.getFloat("valence"), resultSet.getFloat("tempo"),
						resultSet.getInt("time_signature"), resultSet.getString("img_url"));
				songs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Chansons récupérées : " + songs.size()); // Vérifiez la taille de la liste
		return songs;
	}

	private Genre getSafeGenre(String genreString) {
		if (genreString == null || genreString.trim().isEmpty()) {
			return Genre.OTHER;
		}
		try {
			// Supprimer les espaces, remplacer "-" ou autres caractères spéciaux si besoin
			String cleaned = genreString.trim().toUpperCase().replace(" ", "_").replace("-", "_");
			return Genre.valueOf(cleaned);
		} catch (IllegalArgumentException e) {
			System.err.println("Genre inconnu ou invalide : " + genreString);
			return Genre.OTHER;
		}
	}

	// Ajouter une chanson à la base de données
	public void addSong(Song song) {
		String query = "INSERT INTO tracks (track_name, artist_name, preview_url, tags, genre, year, "
				+ "duration_ms, danceability, energy, key_mode, loudness, mode, speechiness, "
				+ "acousticness, instrumentalness, liveness, valence, tempo, time_signature, img_url) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, song.getTrack_name());
			statement.setString(2, song.getArtist_name());
			statement.setString(3, song.getPreview_url());
			statement.setString(4, song.getTags());
			statement.setString(5, song.getGenre().name());
			statement.setInt(6, song.getYear());
			statement.setInt(7, song.getDuration_ms());
			statement.setFloat(8, song.getDanceability());
			statement.setFloat(9, song.getEnergy());
			statement.setInt(10, song.getKey_mode());
			statement.setFloat(11, song.getLoudness());
			statement.setString(12, song.getMode());
			statement.setFloat(13, song.getSpeechiness());
			statement.setFloat(14, song.getAcousticness());
			statement.setFloat(15, song.getInstrumentalness());
			statement.setFloat(16, song.getLiveness());
			statement.setFloat(17, song.getValence());
			statement.setFloat(18, song.getTempo());
			statement.setInt(19, song.getTime_signature());
			statement.setString(20, song.getImg_url());

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Récupère une chanson par son ID
	public Song getSongById(int songId) {
		Song song = null;
		String query = "SELECT * FROM tracks WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, songId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String genreString = resultSet.getString("genre");
				Genre genre = getSafeGenre(genreString);

				song = new Song(resultSet.getInt("id"), resultSet.getString("track_name"),
						resultSet.getString("artist_name"), resultSet.getString("preview_url"),
						resultSet.getString("tags"), genre, resultSet.getInt("year"), resultSet.getInt("duration_ms"),
						resultSet.getFloat("danceability"), resultSet.getFloat("energy"), resultSet.getInt("key_mode"),
						resultSet.getFloat("loudness"), resultSet.getString("mode"), resultSet.getFloat("speechiness"),
						resultSet.getFloat("acousticness"), resultSet.getFloat("instrumentalness"),
						resultSet.getFloat("liveness"), resultSet.getFloat("valence"), resultSet.getFloat("tempo"),
						resultSet.getInt("time_signature"), resultSet.getString("img_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return song;
	}

	// Méthode pour rechercher des chansons par titre ou artiste
	public List<Song> searchSongs(String query) {
		System.out.println("recherche cliquzé");
		List<Song> filteredSongs = new ArrayList<>();
		System.out.println("Recherche avec la requête : " + query); // Ajoutez cette ligne pour vérifier la requête

		for (Song song : songs) {
			if (song.getTrack_name().toLowerCase().contains(query)
					|| song.getArtist_name().toLowerCase().contains(query)) {
				filteredSongs.add(song);
			}
		}

		System.out.println("Résultats trouvés : " + filteredSongs.size()); // Affichez le nombre de résultats
		return filteredSongs;
	}

	public boolean songExists(String songName) {
		String query = "SELECT COUNT(*) FROM tracks WHERE LOWER(track_name) = LOWER(?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, songName);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Récupère des chansons recommandées en fonction de la chanson actuelle
	public List<Song> getRecommendedSongs(Song currentSong) {
		List<Song> recommendedSongs = new ArrayList<>();
		String query = "SELECT * FROM tracks WHERE genre = ? OR artist_name = ? LIMIT 10"; 
																						

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, currentSong.getGenre().name());
			statement.setString(2, currentSong.getArtist_name());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String genreString = resultSet.getString("genre");
				Genre genre = getSafeGenre(genreString);

				Song song = new Song(resultSet.getInt("id"), resultSet.getString("track_name"),
						resultSet.getString("artist_name"), resultSet.getString("preview_url"),
						resultSet.getString("tags"), genre, resultSet.getInt("year"), resultSet.getInt("duration_ms"),
						resultSet.getFloat("danceability"), resultSet.getFloat("energy"), resultSet.getInt("key_mode"),
						resultSet.getFloat("loudness"), resultSet.getString("mode"), resultSet.getFloat("speechiness"),
						resultSet.getFloat("acousticness"), resultSet.getFloat("instrumentalness"),
						resultSet.getFloat("liveness"), resultSet.getFloat("valence"), resultSet.getFloat("tempo"),
						resultSet.getInt("time_signature"), resultSet.getString("img_url"));
				recommendedSongs.add(song);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recommendedSongs;
	}
}
