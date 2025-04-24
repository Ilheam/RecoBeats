<<<<<<< HEAD
package controllers;

import app.Router;
import helpers.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Playlist;
import models.PlaylistTrack;
import models.Song;
import services.PlaylistService;
import services.PlaylistTrackService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistController implements Initializable {

	@FXML
	private ListView<Playlist> playlistListView;

	@FXML
	private ListView<Song> songListView; 

	@FXML
	private TextField playlistNameField;

	private final PlaylistService playlistService = new PlaylistService();
	private final PlaylistTrackService playlistTrackService = new PlaylistTrackService();

	private final ObservableList<Playlist> playlists = FXCollections.observableArrayList();
	private final ObservableList<Song> songsInPlaylist = FXCollections.observableArrayList();
																								

	private int currentUserId;
	private Song currentSong;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		currentUserId = UserSession.getInstance().getCurrentUserId();

		if (currentUserId != -1) {
			loadPlaylists();

			// Lorsqu'on sélectionne une playlist, on affiche ses infos et ses chansons
			playlistListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal != null) {
					playlistNameField.setText(newVal.getName());
					loadSongsForPlaylist(newVal.getId_playlist());
				}
			});

		} else {
			showAlert(Alert.AlertType.ERROR, "Erreur : Aucun utilisateur connecté.");
		}
	}

	private void loadPlaylists() {
		playlists.setAll(playlistService.getPlaylistsByUser(currentUserId));
		playlistListView.setItems(playlists);
	}

	// Charge les chansons associées à la playlist sélectionnée
	private void loadSongsForPlaylist(int playlistId) {
		// Utilisation de la méthode getSongsByPlaylist pour récupérer les chansons complètes de la playlist
		List<Song> songs = playlistTrackService.getSongsByPlaylist(playlistId);

		// Vide la liste des chansons affichées avant de les mettre à jour
		songsInPlaylist.clear();

		// Ajout des chansons complètes à la liste observable
		songsInPlaylist.addAll(songs);

		// Met à jour la vue avec les nouvelles chansons de la playlist
		songListView.setItems(songsInPlaylist);
	}

	@FXML
	private void handleAddPlaylist() {
		String playlistName = playlistNameField.getText().trim();

		if (playlistName.isEmpty()) {
			showAlert(Alert.AlertType.WARNING, "Le nom de la playlist ne peut pas être vide.");
			return;
		}

		Playlist newPlaylist = new Playlist(playlistName, currentUserId);
		playlistService.addPlaylist(newPlaylist);
		loadPlaylists();
		playlistNameField.clear();
	}

	@FXML
	private void handleDeletePlaylist() {
		Playlist selected = playlistListView.getSelectionModel().getSelectedItem();

		if (selected == null) {
			showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner une playlist à supprimer.");
			return;
		}

		playlistService.deletePlaylist(selected.getId_playlist());
		loadPlaylists();
		playlistNameField.clear();
		songsInPlaylist.clear(); // Vide la liste des chansons affichées
	}

	@FXML
	private void handleUpdatePlaylist() {
		Playlist selected = playlistListView.getSelectionModel().getSelectedItem();
		String newName = playlistNameField.getText().trim();

		if (selected == null || newName.isEmpty()) {
			showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner une playlist et entrer un nouveau nom.");
			return;
		}

		selected.setName(newName);
		playlistService.updatePlaylist(selected);
		loadPlaylists();
		playlistNameField.clear();
	}

	@FXML
	private void handleAddToPlaylist() {
		Playlist selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();

		if (selectedPlaylist == null) {
			showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner une playlist.");
			return;
		}

		if (currentSong == null) {
			showAlert(Alert.AlertType.WARNING, "Aucune chanson sélectionnée.");
			return;
		}

		int position = playlistTrackService.getTracksByPlaylist(selectedPlaylist.getId_playlist()).size() + 1;

		playlistTrackService.addTrackToPlaylist(selectedPlaylist.getId_playlist(), currentSong.getTrack_id(), position);

		loadSongsForPlaylist(selectedPlaylist.getId_playlist()); //  Met à jour l'affichage après ajout
		showAlert(Alert.AlertType.INFORMATION, " La chanson a été ajoutée à la playlist avec succès !");
	}

	@FXML
	private void handleBackToHome() {
		Router.navigateTo("/views/home.fxml");
	}

	public void setCurrentSong(Song song) {
		this.currentSong = song;
	}

	private void showAlert(Alert.AlertType type, String message) {
		Alert alert = new Alert(type);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
=======

public class PlaylistController {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
