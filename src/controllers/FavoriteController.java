package controllers;

import services.FavoriteService;
import java.sql.SQLException;
import java.util.List;

public class FavoriteController {

    private FavoriteService favoriteService;

    public FavoriteController() {
        this.favoriteService = new FavoriteService();
    }

    public void addTrackToFavorites(int userId, int trackId) {
        try {
            favoriteService.addToFavorites(userId, trackId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTrackFromFavorites(int userId, int trackId) {
        try {
            favoriteService.removeFromFavorites(userId, trackId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getFavoriteTrackIds(int userId) {
        try {
            return favoriteService.getFavoritesTrackIds(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
