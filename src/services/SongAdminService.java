package services;

import java.sql.*;
import java.util.*;

import models.Genre;
import models.Song;

import database.DBConnection;

public class SongAdminService {

	private List<Song> songs;

	// Récupère toutes les chansons
	public List<Song> getAllSongs() {
		songs = new ArrayList<>();
		String query = "SELECT * FROM tracks";

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







}
