package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Song;
import services.SongService;

import java.util.List;

public class SongDetailsController {

    @FXML private MediaView mediaView;
    @FXML private Text songTitle;
    @FXML private ImageView trackImage;
    @FXML private Text artistName;
    @FXML private Text genre;
    @FXML private Text year;
    @FXML private Text duration;
   // @FXML private ImageView logo;
    @FXML private ListView<Song> otherTracksList; // Changer le type de ListView pour Song
    
    private MediaPlayer mediaPlayer;
    private final SongService songService = new SongService();
    private Song currentSong;

    public SongDetailsController() {
        System.out.println(" Contrôleur de détails de chanson initialisé.");
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
    	songTitle.setText(song.getTrack_name());
        artistName.setText("Artist: " + song.getArtist_name());
        
        // Charge l'image du morceau
        String imageUrl = song.getImg_url(); // Assurez-vous que cette méthode renvoie l'URL ou le chemin de l'image
        trackImage.setImage(new Image(imageUrl));

        
     // Afficher le genre et l'année
        genre.setText("Genre: " + song.getGenre());
        year.setText("Year: " + song.getYear());
        
     // Afficher la durée en minutes et secondes
        int minutes = song.getDuration_ms() / 60000;
        int seconds = (song.getDuration_ms() % 60000) / 1000;
        duration.setText("Duration: " + minutes + "m " + seconds + "s");
        
        
        // Charge le logo de l'application (peut-être une image statique)
       // logo.setImage(new Image("file:/C:/Users/Hp/Desktop/ENSIASD/SEMESTER2/JAVA/PROJECT/JAVA_PROJECT/bin/ressources/assets/images/RECOBEATS.png"));

        // Lancer la musique avec son preview_url
        playSong(song);
    }

    private void playSong(Song song) {
        // Si une chanson est déjà en cours de lecture, on l'arrête avant de jouer une nouvelle chanson
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Arrêter le lecteur audio existant
        }

        String previewUrl = song.getPreview_url();
        try {
            // Créer un nouvel objet Media pour la chanson sélectionnée
            Media media = new Media(previewUrl);
            
            // Créer un nouveau MediaPlayer pour cette chanson
            mediaPlayer = new MediaPlayer(media);
            
            // Associer le MediaPlayer au MediaView pour la lecture
            mediaView.setMediaPlayer(mediaPlayer);
            
            // Démarrer la lecture de la chanson
            mediaPlayer.pause();
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

                    // Ajoute un gestionnaire d'événements pour le clic
                    setOnMouseClicked(event -> {
                        if (song != null) {
                            // Appel de la méthode pour afficher les détails de la chanson dans la même page
                            displaySongDetails(song);
                        }
                    });
                }
            }
        });

        // Ajouter les morceaux à la ListView
        for (int i = 0; i < Math.min(10, otherSongs.size()); i++) {
            otherTracksList.getItems().add(otherSongs.get(i));
        }
    }

    
   
    
    
    private void displaySongDetails(Song song) {
        // Mettre à jour les informations de la chanson dans les composants FXML
        songTitle.setText(song.getTrack_name());
        artistName.setText(song.getArtist_name());
        genre.setText(String.valueOf(song.getGenre()));
        year.setText(String.valueOf(song.getYear()));
        duration.setText(formatDuration(song.getDuration_ms())); // Format de la durée si nécessaire

        // Charger l'image de la chanson
        trackImage.setImage(new Image(song.getImg_url()));

        // Arrêter l'ancienne chanson si elle est en cours de lecture
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Arrêter l'ancien MediaPlayer
        }

        // Charger la musique de la nouvelle chanson
        Media media = new Media(song.getPreview_url());
        mediaPlayer = new MediaPlayer(media); // Créer un nouveau MediaPlayer pour cette chanson
        mediaView.setMediaPlayer(mediaPlayer); // Associer le nouveau MediaPlayer au MediaView
        mediaPlayer.play(); // Démarrer la lecture de la nouvelle chanson
    }


    
 // Méthode pour formater la durée en minutes:secondes
    private String formatDuration(int durationInMs) {
        int minutes = (durationInMs / 1000) / 60;
        int seconds = (durationInMs / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
