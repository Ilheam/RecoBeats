package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import models.Song;
import services.SongService;
import app.Router;
import helpers.UserSession;

import java.util.List;

public class HomeController {

	@FXML
	private TextField searchField;
	@FXML
	private HBox songListContainer;
	@FXML
	private ListView<Song> songListView;
	@FXML
	private VBox sideMenu;
	@FXML
	private ToggleButton menuButton;

	private final SongService songService = new SongService();

	public HomeController() {
		System.out.println("HomeController initialisé !");
	}

	@FXML
	public void initialize() {
		int userId = UserSession.getInstance().getCurrentUserId();
		System.out.println("Connected user ID: " + userId);

		if (songListContainer == null) {
			System.out.println("songListContainer is null!");
		} else {
			System.out.println("Méthode initialize() appelée !");
			loadAllSongs();
		}
	}

	private void loadAllSongs() {
		List<Song> songs = songService.getAllSongs();
		displaySongCards(songs);
	}

	private void displaySongCards(List<Song> songs) {
		songListContainer.getChildren().clear();
		System.out.println("Affichage de " + songs.size() + " cartes de chansons");
		for (Song song : songs) {
			HBox card = createSongCard(song);
			songListContainer.getChildren().add(card);
		}
	}

	private HBox createSongCard(Song song) {
		HBox card = new HBox();
		card.setSpacing(10);

		ImageView songImage = new ImageView(new Image(song.getImg_url()));
		songImage.setFitHeight(100);
		songImage.setFitWidth(100);

		Label songTitle = new Label(song.getTrack_name());
		songTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffffff;");

		HBox hBox = new HBox(10, songImage, songTitle);
		hBox.setStyle("-fx-padding: 15;" + "-fx-background-color: #2a3b8f;" + "-fx-background-radius: 12;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);");

		hBox.setOnMouseEntered(e -> hBox.setStyle("-fx-padding: 15;" + "-fx-background-color: #3d4fc9;"
				+ "-fx-background-radius: 12;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0, 0, 5);"));

		hBox.setOnMouseExited(e -> hBox.setStyle("-fx-padding: 15;" + "-fx-background-color: #2a3b8f;"
				+ "-fx-background-radius: 12;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 4);"));

		hBox.setOnMouseClicked(event -> handleSongSelection(song));

		return hBox;
	}

	private void showSongDetails(Song song) {
		System.out.println("Détails de la chanson: " + song.getTrack_name());
	}

	@FXML
	private void handleSearch(ActionEvent event) {
		String query = searchField.getText().toLowerCase().trim();
		if (!query.isEmpty()) {
			List<Song> filteredSongs = songService.searchSongs(query);
			displaySongCards(filteredSongs);
		}
	}

	public void handleSongSelection(Song selectedSong) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/song_details.fxml"));
			Parent root = loader.load();

			SongDetailsController controller = loader.getController();
			controller.setCurrentSong(selectedSong); // Envoie la chanson sélectionnée

			Scene scene = new Scene(root);
			Stage stage = (Stage) searchField.getScene().getWindow();
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void toggleMenu(ActionEvent event) {
		double targetX = sideMenu.getLayoutX() == 0 ? -250 : 0;
		sideMenu.setLayoutX(targetX);
	}

	@FXML
	public void goToProfile() {
		Router.navigateTo("/views/user.fxml");
	}

	@FXML
	public void goToPlaylists() {
		Router.navigateTo("/views/playlist.fxml");
	}

	@FXML
	public void goToFavorites() {
		Router.navigateTo("/views/favorite.fxml");
	}
}
