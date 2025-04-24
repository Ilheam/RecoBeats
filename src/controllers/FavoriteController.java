package controllers;

import app.Router;
import helpers.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import models.Song;
import services.FavoriteService;

public class FavoriteController {

	@FXML
	private ListView<Song> favoriteListView;

	private FavoriteService favoriteService = new FavoriteService();

	private ObservableList<Song> favorites = FXCollections.observableArrayList();

	private int userId;

	public void initialize() {
		userId = UserSession.getInstance().getCurrentUserId();

		if (userId != -1 && userId != 0) {
			System.out.println("ID utilisateur connecté : " + userId);
			favorites.setAll(favoriteService.getFavoritesByUser(userId));
			favoriteListView.setItems(favorites);
		} else {
			System.out.println("Aucun utilisateur connecté !");
			showAlertNoUserConnected(); // Afficher l'alerte si aucun utilisateur n'est connecté
		}
	}

	@FXML
	public void deleteFavorite(MouseEvent event) {
		// Supprime la chanson sélectionnée des favoris
		Song selectedSong = favoriteListView.getSelectionModel().getSelectedItem();
		if (selectedSong != null) {
			int trackId = selectedSong.getTrack_id();
			favoriteService.deleteFavorite(userId, trackId);
			favorites.remove(selectedSong);
		}
	}

	@FXML
	public void addSongToFavorites(Song song) {
		// Ajoute une chanson aux favoris
		int trackId = song.getTrack_id();
		favoriteService.addSongToFavorites(userId, trackId);

		if (!favorites.contains(song)) {
			favorites.add(song);
		}
	}

	public void showAlertNoUserConnected() {
		// Crée l'alerte
		Alert alert = new Alert(AlertType.WARNING);

		// Définir le titre de l'alerte
		alert.setTitle("Alerte");

		// Définir le texte d'en-tête
		alert.setHeaderText("Aucun utilisateur connecté");

		// Définir le texte du corps de l'alerte
		alert.setContentText("Veuillez vous connecter pour ajouter des favoris.");

		// Afficher l'alerte et attendre la réponse de l'utilisateur
		alert.showAndWait();
	}

	@FXML
	private void handleBackToHome() {
		Router.navigateTo("/views/home.fxml");
	}

}
