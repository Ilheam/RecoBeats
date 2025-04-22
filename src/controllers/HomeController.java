package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Song;
import models.User;
import services.SongService;
import app.Router;

import helpers.SongSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HomeController {

    @FXML private TextField searchField;
    @FXML private HBox songListContainer;
    @FXML private VBox sideMenu;

    private final SongService songService = new SongService();

    public HomeController() {
        System.out.println("HomeController initialisé !");
    }

    @FXML
    public void initialize() {
        System.out.println("Méthode initialize() appelée !");
        int userId = SongSession.getInstance().getCurrentUserId();
        System.out.println("Utilisateur connecté : " + userId);
        Platform.runLater(this::loadAllSongs);
    }

    private void loadAllSongs() {
        List<Song> songs = songService.getAllSongs().stream().limit(15).collect(Collectors.toList());
        displaySongCards(songs);
    }

    private void displaySongCards(List<Song> songs) {
        songListContainer.getChildren().clear();
        for (Song song : songs) {
            HBox card = createSongCard(song);
            songListContainer.getChildren().add(card);
        }
    }

    private HBox createSongCard(Song song) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 15; -fx-background-color: #2a3b8f; -fx-background-radius: 12;" +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);");

        ImageView songImage = new ImageView();
        try {
            if (song.getImg_url() != null && !song.getImg_url().isEmpty()) {
                Image img = new Image(song.getImg_url(), true);
                songImage.setImage(img);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }

        songImage.setFitHeight(100);
        songImage.setFitWidth(100);

        Label songTitle = new Label(song.getTrack_name());
        songTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        card.getChildren().addAll(songImage, songTitle);

        // Effets visuels au survol
        card.setOnMouseEntered(e -> card.setStyle("-fx-padding: 15; -fx-background-color: #3d4fc9;" +
                                                  "-fx-background-radius: 12;" +
                                                  "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 5);"));

        card.setOnMouseExited(e -> card.setStyle("-fx-padding: 15; -fx-background-color: #2a3b8f;" +
                                                 "-fx-background-radius: 12;" +
                                                 "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);"));

        card.setOnMouseClicked(event -> handleSongSelection(song));

        return card;
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase().trim();
        if (!query.isEmpty()) {
            List<Song> filteredSongs = songService.searchSongs(query);
            displaySongCards(filteredSongs);
        } else {
            loadAllSongs();
        }
    }

    private void handleSongSelection(Song song) {
        if (song != null) {
            System.out.println("Chanson sélectionnée : " + song.getTrack_name());
            SongSession.getInstance().setSelectedSong(song);

            int currentUserId = SongSession.getInstance().getCurrentUserId();
            if (currentUserId != -1) {
                System.out.println("Utilisateur connecté : " + currentUserId);
            } else {
                System.out.println("Aucun utilisateur connecté.");
            }

            Router.navigateTo("/views/song_details.fxml");
        }
    }

    @FXML
    private void toggleMenu() {
        sideMenu.setVisible(!sideMenu.isVisible());
    }

    @FXML
    private void goToProfile() {
        Router.navigateTo("/views/user.fxml");
    }

    @FXML
    private void goToPlaylists() {
        Router.navigateTo("/views/playlist.fxml");
    }

    @FXML
    private void goToFavorites() {
        Router.navigateTo("/views/favorite.fxml");
    }
}
