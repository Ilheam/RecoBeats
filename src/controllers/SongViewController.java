package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;

import java.net.URL;
import java.util.ResourceBundle;

import database.DBConnection;

import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Song;
import services.SongService;

public class SongViewController implements Initializable {

    @FXML private TableView<Song> tableSongs;
    @FXML private TableColumn<Song, Integer> colId;
    @FXML private TableColumn<Song, String> colName;
    @FXML private TableColumn<Song, String> colArtist;
    @FXML private TableColumn<Song, String> colGenre; 
    @FXML private TableColumn<Song, Integer> colYear;
    @FXML private TableColumn<Song, Integer> colDuration;
    @FXML private TableColumn<Song, Void> deleteColumn;
    @FXML private TextField searchField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Liaison des colonnes avec les attributs de la classe Song
        colId.setCellValueFactory(new PropertyValueFactory<>("track_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("track_name"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist_name"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration_ms"));

        // Charger les chansons depuis le service
        SongService service = new SongService();
        List<Song> songs = service.getAllSongs();
        tableSongs.setItems(FXCollections.observableArrayList(songs));
        tableSongs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Référence à l'instance actuelle du contrôleur
        SongViewController controller = this;

        // Configuration de la colonne "Supprimer"
        deleteColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    Song song = getTableView().getItems().get(getIndex());
                    controller.deleteSong(song);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
    }

    private boolean showConfirmationAlert(String title, String message) {
        // Création de l'alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Création de deux boutons : "OK" et "Annuler"
        ButtonType buttonTypeOK = new ButtonType("OK");
        ButtonType buttonTypeCancel = new ButtonType("Annuler");
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Affichage de l'alerte et attente de la réponse de l'utilisateur
        Optional<ButtonType> result = alert.showAndWait();

        // Retourne true si l'utilisateur a cliqué sur "OK", sinon false
        return result.isPresent() && result.get() == buttonTypeOK;
    }
    private void deleteSong(Song song) {
        // Appel à la méthode de confirmation
        if (showConfirmationAlert("Confirmation de Suppression", "Êtes-vous sûr de vouloir supprimer cette chanson ?")) {
            // Si l'utilisateur confirme, procéder à la suppression
            String query = "DELETE FROM tracks WHERE id = ?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, song.getTrack_id());
                stmt.executeUpdate();

                // Supprimer aussi de la TableView
                tableSongs.getItems().remove(song);

            } catch (SQLException e) {
                e.printStackTrace();

                // Affichage de l'alerte d'erreur si la suppression échoue
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("Erreur de suppression : " + e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            // Si l'utilisateur annule, ne rien faire
            System.out.println("Suppression annulée");
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onSearchClicked() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un nom de chanson.", Alert.AlertType.WARNING);
            return;
        }

        SongService service = new SongService();
        boolean found = service.songExists(keyword);

        if (found) {
            showAlert("Succès", "La chanson existe dans la base de données.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Introuvable", "Aucune chanson trouvée avec ce nom.", Alert.AlertType.WARNING);
        }
    }



}
