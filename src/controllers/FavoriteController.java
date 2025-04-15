package controllers;

import models.Favorite;
import models.Song;
import services.FavoriteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class FavoriteController {

    @FXML
    private ListView<Favorite> favoriteListView;

    private FavoriteService favoriteService = new FavoriteService(); 

    private ObservableList<Favorite> favorites = FXCollections.observableArrayList();

   
    public void initialize() {
        
        favorites.setAll(favoriteService.getAllFavorites());
        favoriteListView.setItems(favorites);
    }

    @FXML
    public void addSongToFavorites(Song song) {
        int userId = 1; 
        int trackId = song.getTrack_id();
        favoriteService.addSongToFavorites(userId, trackId);
        
        Favorite userFavorite = findUserFavorite(userId);
        if (userFavorite != null) {
            userFavorite.addFavorite(song);
        }
    }

    @FXML
    public void deleteFavorite(MouseEvent event) {
        Favorite selectedFavorite = favoriteListView.getSelectionModel().getSelectedItem();
        if (selectedFavorite != null) {
            int favoriteId = selectedFavorite.getId_playlist(); 
            favoriteService.deleteFavorite(favoriteId);
            
            favorites.remove(selectedFavorite);
        }
    }

    private Favorite findUserFavorite(int userId) {
        for (Favorite favorite : favorites) {
            if (favorite.getUser_id() == userId) {
                return favorite;
            }
        }
        return null;
    }
}
