package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import models.Song;
import services.SongService;
import app.Router;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;

public class HomeController {

    @FXML private TextField searchField;
    @FXML private HBox songListContainer;
    @FXML private ListView<Song> songListView;

    private final SongService songService = new SongService();

    public HomeController() {
        System.out.println("HomeController initialisé !");
    }
    
    
    @FXML
    public void initialize() {
    	System.out.println("Méthode initialize() appelée !");
        // Assurez-vous que l'élément est bien initialisé avant de l'utiliser
        if (songListContainer == null) {
            System.out.println("songListContainer is null!");
        } else {
        	System.out.println("Méthode initialize() appelée !");
            loadAllSongs();  // Exemple d'appel de méthode
        }
    }

    private void loadAllSongs() {
        List<Song> songs = songService.getAllSongs();
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
        HBox card = new HBox();
        card.setSpacing(10);

        // Image de la chanson
        ImageView songImage = new ImageView(new Image(song.getImg_url()));
        songImage.setFitHeight(100);
        songImage.setFitWidth(100);

        // Nom du track
        Label songTitle = new Label(song.getTrack_name());
        songTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");

        // Layout pour afficher le tout
        HBox hBox = new HBox(10, songImage, songTitle);
        hBox.setStyle("-fx-padding: 10; -fx-border-radius: 10; -fx-background-color: #f0f0f0;");
        hBox.setOnMouseClicked(event -> showSongDetails(song));

        return hBox;
    }

    private void showSongDetails(Song song) {
        // Ouvrir une nouvelle fenêtre ou section avec les détails de la chanson
        // Vous pouvez utiliser un FXMLLoader pour charger un autre fichier FXML contenant les détails
        System.out.println("Détails de la chanson: " + song.getTrack_name());
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        String query = searchField.getText().toLowerCase();
        List<Song> filteredSongs = songService.searchSongs(query);
        displaySongCards(filteredSongs);
    }
    
    
 // Lors du clic sur une chanson, navigate to song details
    @FXML
    public void handleSongSelection() {
        Song selectedSong = songListView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            Router.navigateTo("/views/song_details.fxml");
        }
    }
    
    public void loadSongs() {
        try {
            // Connexion à la base de données (ajustez l'URL et les informations de connexion)
            String url = "jdbc:mysql://localhost:3306/tracks_db";
            String username = "root";
            String password = "";

            // Charger le driver JDBC (selon la base de données)
            Connection connection = DriverManager.getConnection(url, username, password);

            // Créer une requête pour récupérer les chansons
            String query = "SELECT * FROM songs"; // Assurez-vous que la table 'songs' existe dans votre BDD
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Ajouter les boutons dynamiquement dans le HBox
            while (resultSet.next()) {
                String songName = resultSet.getString("name");
                String artistName = resultSet.getString("artist");

                System.out.println("Chanson récupérée : " + songName + " - " + artistName);

                Button songButton = new Button(songName + " - " + artistName);
                songButton.setOnAction(event -> {
                    System.out.println("Chanson sélectionnée : " + songName);
                });

                if (songListContainer == null) {
                    System.out.println("songListContainer est NULL !");
                } else {
                    songListContainer.getChildren().add(songButton);
                    System.out.println("Bouton ajouté pour : " + songName);
                }
            }


            // Fermer la connexion à la base de données
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
}
