<<<<<<< HEAD
package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import models.Song;
import services.FavoriteService;
import services.SongService;
import helpers.KNNRecommender;
import helpers.UserSession;

import java.util.ArrayList;
import java.util.List;

import app.Router;

public class SongDetailsController {

	@FXML
	private MediaView mediaView;
	@FXML
	private Text songTitle;
	@FXML
	private ImageView trackImage;
	@FXML
	private Text artistName;
	@FXML
	private Text genre;
	@FXML
	private Text year;
	@FXML
	private Text duration;
	@FXML
	private ListView<Song> otherTracksList;

	private MediaPlayer mediaPlayer;
	private final SongService songService = new SongService();
	private Song currentSong;
	private List<Song> recommendedSongs = new ArrayList<>();

	public SongDetailsController() {
		System.out.println("Contrôleur de détails de chanson initialisé.");
	}

	@FXML
	public void initialize() {
		/*
		 * List<Song> allSongs = songService.getAllSongs(); if (!allSongs.isEmpty()) {
		 * currentSong = allSongs.get(0); initSongDetails(currentSong); }
		 * 
		 * loadRecommendedSongs();
		 */
	}

	public void setCurrentSong(Song song) {
		this.currentSong = song;
		initSongDetails(song);
		loadRecommendedSongs();
	}

	private void initSongDetails(Song song) {
		System.out.println("Initialisation des détails : " + song.getTrack_name());
		songTitle.setText(song.getTrack_name());
		artistName.setText("Artist: " + song.getArtist_name());
		trackImage.setImage(new Image(song.getImg_url()));
		genre.setText("Genre: " + song.getGenre());
		year.setText("Year: " + song.getYear());

		int minutes = song.getDuration_ms() / 60000;
		int seconds = (song.getDuration_ms() % 60000) / 1000;
		duration.setText("Duration: " + minutes + "m " + seconds + "s");

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

	@FXML
	public void handleAddToFavorites() {
		if (currentSong != null) {
			int userId = UserSession.getInstance().getCurrentUserId();
			int trackId = currentSong.getTrack_id();

			FavoriteService favoriteService = new FavoriteService();
			favoriteService.addSongToFavorites(userId, trackId);

			showAlert("Succès", "Chanson ajoutée aux favoris avec succès !", Alert.AlertType.INFORMATION);
		} else {
			showAlert("Erreur", "Aucune chanson sélectionnée.", Alert.AlertType.ERROR);
		}
	}

	@FXML
	private void handleAddToPlaylist() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playlist.fxml"));
			Parent playlistView = loader.load();

			// Passer la chanson sélectionnée au contrôleur de playlist
			PlaylistController controller = loader.getController();
			controller.setCurrentSong(currentSong);

			mediaView.getScene().setRoot(playlistView);
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Erreur", "Impossible d'ouvrir la page des playlists.", Alert.AlertType.ERROR);
		}
	}

	@FXML
	private void loadRecommendedSongs() {
		// Récupérer toutes les chansons depuis service
		List<Song> allSongs = songService.getAllSongs();

		// Créer une instance de KNNRecommender et trouver les morceaux similaires
		KNNRecommender knnRecommender = new KNNRecommender();
		List<Song> recommendedSongs = knnRecommender.findSimilarTracks(currentSong, allSongs, 10); // 10 morceaux
																									

		// Mise à jour de la ListView avec les morceaux recommandés
		ObservableList<Song> observableSongs = FXCollections.observableArrayList(recommendedSongs);
		otherTracksList.setItems(observableSongs);

		// Customisation de l'affichage des morceaux dans la ListView
		otherTracksList.setCellFactory(param -> new ListCell<Song>() {
			@Override
			protected void updateItem(Song item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					// Créer un HBox pour afficher l'image et le texte
					HBox hBox = new HBox(10);

					// Si l'image existe, afficher l'image de la chanson
					ImageView songImage;
					if (item.getImg_url() != null && !item.getImg_url().isEmpty()) {
						songImage = new ImageView(new Image(item.getImg_url()));
					} else {
						songImage = new ImageView(new Image("@/RECOBEATS.png")); // Image par défaut
					}
					songImage.setFitHeight(50);
					songImage.setFitWidth(50);

					// Affichage du nom de la chanson et de l'artiste
					Text songText = new Text(item.getTrack_name() + " - " + item.getArtist_name());

					hBox.getChildren().addAll(songImage, songText);
					setGraphic(hBox);

					// Gérer le clic sur un morceau recommandé
					setOnMouseClicked(event -> {
						if (item != null) {
							displaySongDetails(item); // Afficher les détails du morceau
						}
					});
				}
			}
		});
	}

	private void displaySongDetails(Song song) {
		currentSong = song;

		// Infos chanson
		songTitle.setText(song.getTrack_name());
		artistName.setText(song.getArtist_name());
		genre.setText(String.valueOf(song.getGenre()));
		year.setText(String.valueOf(song.getYear()));
		duration.setText(formatDuration(song.getDuration_ms()));
		trackImage.setImage(new Image(song.getImg_url()));

		// Lecture du morceau
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		Media media = new Media(song.getPreview_url());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();

		// Chargement des recommandations
		recommendedSongs = songService.getRecommendedSongs(song); // récupère les objets Song
		ObservableList<Song> observableSongs = FXCollections.observableArrayList(recommendedSongs);
		otherTracksList.setItems(observableSongs);

		// Customiser l'affichage des titres dans la ListView
		otherTracksList.setCellFactory(param -> new ListCell<Song>() {
			@Override
			protected void updateItem(Song item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null); // Enlève l'affichage de l'image si l'élément est vide
				} else {
					// Créer un HBox pour afficher l'image et le texte
					HBox hBox = new HBox(10);

					// Affichage de l'image
					ImageView songImage = new ImageView(new Image(item.getImg_url()));
					songImage.setFitHeight(50);
					songImage.setFitWidth(50);

					// Affichage du nom et de l'artiste
					Text songText = new Text(item.getTrack_name() + " - " + item.getArtist_name());

					hBox.getChildren().addAll(songImage, songText);
					setGraphic(hBox);
				}
			}
		});

		// Gérer le clic sur un morceau recommandé
		otherTracksList.setOnMouseClicked(event -> {
			Song selectedSong = otherTracksList.getSelectionModel().getSelectedItem();
			if (selectedSong != null) {
				displaySongDetails(selectedSong); // afficher ses détails
			}
		});
	}

	private String formatDuration(int durationInMs) {
		int minutes = (durationInMs / 1000) / 60;
		int seconds = (durationInMs / 1000) % 60;
		return String.format("%02d:%02d", minutes, seconds);
	}

	private void showAlert(String title, String content, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@FXML
	public void goBackToHome() {
		Router.navigateTo("/views/home.fxml");
	}

=======

public class SongDetailsController {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
