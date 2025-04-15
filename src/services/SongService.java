package services;

import java.sql.*;
import java.util.*;

import models.Genre;
import models.Song;

import database.DBConnection;

public class SongService {

    // Récupère toutes les chansons
	public List<Song> getAllSongs() {
	    List<Song> songs = new ArrayList<>();
	    String query = "SELECT * FROM track";

	    try (Connection connection = DBConnection.getConnection();
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(query)) {

	        while (resultSet.next()) {
	            String genreString = resultSet.getString("genre");
	            Genre genre = getSafeGenre(genreString);

	            Song song = new Song(
	                resultSet.getInt("id"),
	                resultSet.getString("track_name"),
	                resultSet.getString("artist_name"),
	                resultSet.getString("preview_url"),
	                resultSet.getString("tags"),
	                genre,
	                resultSet.getInt("year"),
	                resultSet.getInt("duration_ms"),
	                resultSet.getFloat("danceability"),
	                resultSet.getFloat("energy"),
	                resultSet.getInt("key_mode"),
	                resultSet.getFloat("loudness"),
	                resultSet.getString("mode"),
	                resultSet.getFloat("speechiness"),
	                resultSet.getFloat("acousticness"),
	                resultSet.getFloat("instrumentalness"),
	                resultSet.getFloat("liveness"),
	                resultSet.getFloat("valence"),
	                resultSet.getFloat("tempo"),
	                resultSet.getInt("time_signature"),
	                resultSet.getString("img_url")
	            );
	            songs.add(song);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
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
        String query = "INSERT INTO track (track_name, artist_name, preview_url, tags, genre, year, " +
                       "duration_ms, danceability, energy, key_mode, loudness, mode, speechiness, " +
                       "acousticness, instrumentalness, liveness, valence, tempo, time_signature, img_url) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        String query = "SELECT * FROM track WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, songId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String genreString = resultSet.getString("genre");
                Genre genre = getSafeGenre(genreString);

                song = new Song(
                        resultSet.getInt("id"),
                        resultSet.getString("track_name"),
                        resultSet.getString("artist_name"),
                        resultSet.getString("preview_url"),
                        resultSet.getString("tags"),
                        genre,
                        resultSet.getInt("year"),
                        resultSet.getInt("duration_ms"),
                        resultSet.getFloat("danceability"),
                        resultSet.getFloat("energy"),
                        resultSet.getInt("key_mode"),
                        resultSet.getFloat("loudness"),
                        resultSet.getString("mode"),
                        resultSet.getFloat("speechiness"),
                        resultSet.getFloat("acousticness"),
                        resultSet.getFloat("instrumentalness"),
                        resultSet.getFloat("liveness"),
                        resultSet.getFloat("valence"),
                        resultSet.getFloat("tempo"),
                        resultSet.getInt("time_signature"),
                        resultSet.getString("img_url")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return song;
    }
}
