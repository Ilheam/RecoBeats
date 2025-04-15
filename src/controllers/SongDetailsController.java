package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.Song;
import services.SongService;

import java.util.List;

public class SongDetailsController {

    @FXML private MediaView mediaView;
    @FXML private ImageView trackImage;
    @FXML private Text songTitle;
    @FXML private ImageView logo;
    @FXML private ListView<Song> otherTracksList; // Changer le type de ListView pour Song
    
    private MediaPlayer mediaPlayer;
    private final SongService songService = new SongService();
    private Song currentSong;

    public SongDetailsController() {
        System.out.println("🎯 Contrôleur initialisé !");
    }

    @FXML
    public void initialize() {
        // Récupère une chanson dynamique depuis la base de données (par exemple, la première chanson)
        currentSong = songService.getAllSongs().get(0); // Assurez-vous qu'il y a au moins une chanson dans la base de données
        
        // Initialiser l'affichage du morceau actuel
        initSongDetails(currentSong);
        
        // Charger les autres morceaux
        loadOtherTracks();
    }

    private void initSongDetails(Song song) {
        // Affiche le titre de la chanson
        songTitle.setText(song.getTrack_name() + " - " + song.getArtist_name());

        // Charge l'image du morceau
        String imageUrl = song.getImg_url(); // Assurez-vous que cette méthode renvoie l'URL ou le chemin de l'image
        trackImage.setImage(new Image(imageUrl));

        // Charge le logo de l'application (peut-être une image statique)
        logo.setImage(new Image("file:/C:/Users/Hp/Desktop/ENSIASD/SEMESTER2/JAVA/PROJECT/JAVA_PROJECT/bin/ressources/assets/images/RECOBEATS.png"));

        // Lancer la musique avec son preview_url
        playSong(song);
    }

    private void playSong(Song song) {
        String previewUrl = song.getPreview_url();
        try {
            Media media = new Media(previewUrl);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture de la musique : " + e.getMessage());
        }
    }

    @FXML
    public void handlePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    public void handlePause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    public void handleAddToFavorites() {
        // Ajouter la chanson aux favoris
        System.out.println("Chanson ajoutée aux favoris !");
    }

    @FXML
    public void handleAddToPlaylist() {
        // Ajouter la chanson à une playlist
        System.out.println("Chanson ajoutée à une playlist !");
    }

    private void loadOtherTracks() {
        List<Song> otherSongs = songService.getAllSongs(); // Récupérer toutes les chansons

        // Définir un CellFactory personnalisé pour la ListView
        otherTracksList.setCellFactory(listView -> new ListCell<Song>() {
            @Override
            protected void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);
                if (empty || song == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Créer un HBox pour afficher l'image et le texte
                    HBox hBox = new HBox(10);
                    ImageView songImage = new ImageView(new Image(song.getImg_url()));
                    songImage.setFitHeight(50);
                    songImage.setFitWidth(50);

                    Text songText = new Text(song.getTrack_name() + " - " + song.getArtist_name());

                    hBox.getChildren().addAll(songImage, songText);
                    setGraphic(hBox);
                }
            }
        });

        // Ajouter les morceaux à la ListView
        for (int i = 0; i < Math.min(10, otherSongs.size()); i++) {
            otherTracksList.getItems().add(otherSongs.get(i));
        }
    }
}
