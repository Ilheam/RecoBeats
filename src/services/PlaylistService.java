<<<<<<< HEAD
package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.*;
import database.DBConnection;

public class PlaylistService {

	// Méthode pour ajouter une playlist
	public void addPlaylist(Playlist playlist) {
		String query = "INSERT INTO playlists (name, userId) VALUES (?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, playlist.getName());
			statement.setInt(2, playlist.getUser_id()); 

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Playlist ajoutée avec succès.");
			} else {
				System.out.println("Échec de l'ajout de la playlist.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour obtenir les playlists d'un utilisateur par ID
	public List<Playlist> getPlaylistsByUser(int userId) {
		List<Playlist> playlists = new ArrayList<>();
		String query = "SELECT * FROM playlists WHERE userId = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Playlist playlist = new Playlist(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getInt("userId"));
				playlists.add(playlist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playlists;
	}

	// Méthode pour obtenir toutes les playlists
	public List<Playlist> getAllPlaylists() {
		List<Playlist> playlists = new ArrayList<>();
		String query = "SELECT * FROM playlists";

		try (Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			while (resultSet.next()) {
				Playlist playlist = new Playlist(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getInt("userId"));
				playlists.add(playlist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playlists;
	}

	// Méthode pour supprimer une playlist
	public void deletePlaylist(int id_playlist) {
		String query = "DELETE FROM playlists WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, id_playlist);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Playlist supprimée avec succès.");
			} else {
				System.out.println("Échec de la suppression de la playlist.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode pour mettre à jour une playlist
	public void updatePlaylist(Playlist playlist) {
		String query = "UPDATE playlists SET name = ?, userId = ? WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, playlist.getName());
			statement.setInt(2, playlist.getUser_id());
			statement.setInt(3, playlist.getId_playlist());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Playlist mise à jour avec succès.");
			} else {
				System.out.println("Échec de la mise à jour de la playlist.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
=======

public class PlaylistService {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
