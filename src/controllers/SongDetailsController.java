package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.Playlist;
import models.Song;
import models.User;
import services.FavoriteService;
import services.PlaylistService;
import services.SongService;

import java.util.List;
import java.util.stream.Collectors;

import helpers.SongSession;


public class SongDetailsController {

    @FXML private MediaView mediaView;
    @FXML private Text songTitle;
    @FXML private ImageView trackImage;
    @FXML private Text artistName;
    @FXML private Text genre;
    @FXML private Text year;
    @FXML private Text duration;
    @FXML private ListView<Song> otherTracksList;

    private PlaylistService playlistService = new PlaylistService();
    private FavoriteService favoriteService = new FavoriteService();
    private MediaPlayer mediaPlayer;
    private final SongService songService = new SongService();
    private Song currentSong;
    private User currentUser;
    private int currentTrackId;

    public SongDetailsController() {
        System.out.println("Contrôleur de détails de chanson initialisé.");
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setTrackId(int trackId) {
        this.currentTrackId = trackId;
    }

    @FXML
    public void initialize() {
        currentSong = SongSession.getInstance().getSelectedSong();
        
        if (currentSong != null) {
            currentTrackId = currentSong.getTrack_id();
            initSongDetails(currentSong);
            loadRecommendedTracks();
        }

        otherTracksList.setOnMouseClicked(event -> {
            Song selectedSong = otherTracksList.getSelectionModel().getSelectedItem();
            if (selectedSong != null) {
                currentSong = selectedSong;
                currentTrackId = selectedSong.getTrack_id();
                displaySongDetails(selectedSong);
            }
        });
    }


    private void initSongDetails(Song song) {
        songTitle.setText(song.getTrack_name());
        artistName.setText("Artist: " + song.getArtist_name());
        genre.setText("Genre: " + song.getGenre());
        year.setText("Year: " + song.getYear());
        duration.setText("Duration: " + formatDuration(song.getDuration_ms()));
        trackImage.setImage(new Image(song.getImg_url()));
        playSong(song);
    }

    private void playSong(Song song) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        try {
            Media media = new Media(song.getPreview_url());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
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

    public void handleAddToFavorites() {
        if (currentUser != null) {
            int userId = currentUser.getUserId();
            // Logique d'ajout aux favoris
        } else {
            System.out.println("L'utilisateur n'est pas connecté.");
            // Gérer l'erreur, afficher un message ou rediriger l'utilisateur
        }
    }


    public void handleAddToPlaylist() {
        try {
            int userId = currentUser.getUserId();
            List<Playlist> playlists = playlistService.getUserPlaylists(userId);

            if (playlists.isEmpty()) {
                System.out.println("Aucune playlist trouvée pour l'utilisateur.");
                return;
            }

            Playlist selectedPlaylist = playlists.get(0); // Simplifié pour l’exemple
            playlistService.addSongToPlaylist(selectedPlaylist.getUser_id(), currentTrackId);
            System.out.println("Chanson ajoutée à la playlist avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    private void loadRecommendedTracks() {
        List<Song> allSongs = songService.getAllSongs();

        List<Song> recommendedSongs = allSongs.stream()
                .filter(song -> !song.equals(currentSong))
                .filter(song -> song.getGenre().toString().equalsIgnoreCase(currentSong.getGenre().toString())) 
                .limit(5)
                .collect(Collectors.toList());

        otherTracksList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);
                if (empty || song == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    ImageView image = new ImageView(new Image(song.getImg_url()));
                    image.setFitHeight(50);
                    image.setFitWidth(50);
                    Text text = new Text(song.getTrack_name() + " - " + song.getArtist_name());
                    text.setStyle("-fx-fill: white;");
                    hBox.getChildren().addAll(image, text);
                    setGraphic(hBox);
                }
            }
        });

        otherTracksList.getItems().setAll(recommendedSongs);
    }

    private void displaySongDetails(Song song) {
        songTitle.setText(song.getTrack_name());
        artistName.setText("Artist: " + song.getArtist_name());
        genre.setText("Genre: " + song.getGenre());
        year.setText("Year: " + song.getYear());
        duration.setText("Duration: " + formatDuration(song.getDuration_ms()));
        trackImage.setImage(new Image(song.getImg_url()));

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        try {
            Media media = new Media(song.getPreview_url());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Erreur lecture nouvelle chanson : " + e.getMessage());
        }
    }

    private String formatDuration(int durationInMs) {
        int minutes = durationInMs / 60000;
        int seconds = (durationInMs % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
