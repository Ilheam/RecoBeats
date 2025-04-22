package controllers;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import services.PlaylistService;

public class AddSongToPlaylistController {

    @FXML
    private ComboBox<Integer> playlistComboBox; // Liste des playlists disponibles
    @FXML
    private ComboBox<Integer> songComboBox; // Liste des chansons disponibles

    private PlaylistService playlistService = new PlaylistService(); // Service pour gérer les playlists

    // Méthode pour ajouter une chanson à la playlist
    @FXML
    private void addSongToPlaylist() {
        Integer playlistId = playlistComboBox.getValue();
        Integer songId = songComboBox.getValue();

        // Vérification que la chanson et la playlist sont sélectionnées
        if (playlistId == null || songId == null) {
            showAlert(Alert.AlertType.WARNING, "Veuillez sélectionner une playlist et une chanson.");
            return;
        }

        // Ajouter la chanson à la playlist via le service
        try {
			playlistService.addSongToPlaylist(playlistId, songId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        showAlert(Alert.AlertType.INFORMATION, "Chanson ajoutée à la playlist !");
    }

    // Méthode pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
