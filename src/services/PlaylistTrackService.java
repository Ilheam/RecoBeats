package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import models.Genre;
import models.PlaylistTrack;
import models.Song;

public class PlaylistTrackService {

	// Ajouter une chanson à une playlist
	public void addTrackToPlaylist(int playlistId, int trackId, int position) {
		String query = "INSERT INTO playlisttrack (playlistId, trackId, position) VALUES (?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, playlistId);
			stmt.setInt(2, trackId);
			stmt.setInt(3, position);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Supprimer une chanson d'une playlist
	public void removeTrackFromPlaylist(int playlistId, int trackId) {
		String query = "DELETE FROM playlisttrack WHERE playlistId = ? AND trackId = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, playlistId);
			stmt.setInt(2, trackId);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Récupérer toutes les chansons d'une playlist
	public List<PlaylistTrack> getTracksByPlaylist(int playlistId) {
		List<PlaylistTrack> tracks = new ArrayList<>();
		String query = "SELECT * FROM playlisttrack WHERE playlistId = ? ORDER BY position";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, playlistId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				PlaylistTrack pt = new PlaylistTrack(rs.getInt("id"), rs.getInt("playlistId"), rs.getInt("trackId"),
						rs.getInt("position"));
				tracks.add(pt);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tracks;
	}

	// Vérifie si une chanson est déjà dans une playlist
	public boolean isTrackInPlaylist(int playlistId, int trackId) {
		String query = "SELECT COUNT(*) FROM playlisttrack WHERE playlistId = ? AND trackId = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, playlistId);
			stmt.setInt(2, trackId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Song> getSongsByPlaylist(int playlistId) {
		List<Song> songs = new ArrayList<>();
		String query = "SELECT t.id, t.track_name, t.artist_name, t.preview_url, t.tags, t.genre, t.year, "
				+ "t.duration_ms, t.danceability, t.energy, t.key_mode, t.loudness, t.mode, t.speechiness, "
				+ "t.acousticness, t.instrumentalness, t.liveness, t.valence, t.tempo, t.time_signature, t.img_url "
				+ "FROM playlisttrack pt "
				+ "JOIN tracks t ON pt.trackId = t.id "
				+ "WHERE pt.playlistId = ? ORDER BY pt.position";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, playlistId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Song song = new Song(
					rs.getInt("id"),
					rs.getString("track_name"),
					rs.getString("artist_name"),
					rs.getString("preview_url"),
					rs.getString("tags"),
					Genre.fromString(rs.getString("genre")), 
					rs.getInt("year"),
					rs.getInt("duration_ms"),
					rs.getFloat("danceability"),
					rs.getFloat("energy"),
					rs.getInt("key_mode"),
					rs.getFloat("loudness"),
					rs.getString("mode"),
					rs.getFloat("speechiness"),
					rs.getFloat("acousticness"),
					rs.getFloat("instrumentalness"),
					rs.getFloat("liveness"),
					rs.getFloat("valence"),
					rs.getFloat("tempo"),
					rs.getInt("time_signature"),
					rs.getString("img_url")
				);
				songs.add(song);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return songs;
	}


}
