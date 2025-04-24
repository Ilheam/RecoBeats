package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import services.UserService;
import helpers.UserSession; 

import java.io.IOException;
import java.util.List;

import app.Router;

public class UserController {

    private final UserService userService;

    @FXML
    private Label firstNameLabel;  // Affiche le prénom
    @FXML
    private Label lastNameLabel;   // Affiche le nom
    @FXML
    private Label usernameLabel;   // Affiche le nom d'utilisateur
    @FXML
    private Circle profileImageCircle;  // Affiche le cercle avec la première lettre du prénom
    @FXML
    private Text profileInitialText;    // Affiche la première lettre du prénom dans le cercle

    @FXML private TextField userIdField;
    @FXML private TextField playlistSongField;
    @FXML private TextField favoritesSongField;
    @FXML private TextField historySongField;

    @FXML private ListView<String> playlistListView;
    @FXML private ListView<String> favoritesListView;
    @FXML private ListView<String> historyListView;

    
    public UserController() {
        this.userService = new UserService();
    }

    @FXML
    private void handleDisplayUserProfile() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            User user = userService.getUserProfile(userId);
            if (user != null) {
                System.out.println("Profil utilisateur :\n" + user);
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID utilisateur invalide.");
        }
    }
    
    @FXML
    private void handleBackToHome() {
        Router.navigateTo("/views/home.fxml");
    }
    @FXML
    private void handleBackToPlaylist() {
        Router.navigateTo("/views/playlist.fxml");
    }
    @FXML
    private void handleBackToFavorite() {
        Router.navigateTo("/views/favorite.fxml");
    }


    @FXML
    private void handleEditButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    
    @FXML
    private void handleFavoriteButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/favorite.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handlePlayListButtonAction(ActionEvent event) {
        try {
        	System.out.println("playy");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playlist.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDisplayUserPlaylist() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            List<String> playlist = userService.getUserPlaylist(userId);
            playlistListView.getItems().setAll(playlist);
        } catch (NumberFormatException e) {
            System.out.println("ID utilisateur invalide.");
        }
    }

    @FXML
    private void handleUpdateUserPlaylist() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            String song = playlistSongField.getText();
            boolean success = userService.updatePlaylist(userId, song, true);
            System.out.println(success ? "Playlist mise à jour." : "Échec de la mise à jour.");
        } catch (NumberFormatException e) {
            System.out.println("ID utilisateur invalide.");
        }
    }

    @FXML
    private void handleDisplayUserFavorites() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            List<String> favorites = userService.getUserFavorites(userId);
            favoritesListView.getItems().setAll(favorites);
        } catch (NumberFormatException e) {
            System.out.println("ID utilisateur invalide.");
        }
    }

    @FXML
    private void handleUpdateUserFavorites() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            String song = favoritesSongField.getText();
            boolean success = userService.updateFavorites(userId, song, true);
            System.out.println(success ? "Favoris mis à jour." : "Échec de la mise à jour.");
        } catch (NumberFormatException e) {
            System.out.println("ID utilisateur invalide.");
        }
    }

    

 


  
    // Fusion des deux méthodes initialize()
    @FXML
    public void initialize() {
        // Récupère l'utilisateur actuel depuis la session
        int currentUserId = UserSession.getInstance().getCurrentUserId();  
        
        if (currentUserId != 0) {  // Vérifie que l'utilisateur est connecté
            User currentUser = userService.getUserProfile(currentUserId);  // Récupère l'utilisateur connecté
            
            if (currentUser != null) {
                // Affiche le prénom, nom et le username
                firstNameLabel.setText(currentUser.getFirstName());
                lastNameLabel.setText(currentUser.getLastName());
                usernameLabel.setText("@" + currentUser.getUserName());

                // Affiche la première lettre du prénom dans le cercle
                String firstLetter = currentUser.getUserName().substring(0, 1).toUpperCase();
                profileInitialText.setText(firstLetter);
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        } else {
            System.out.println("Utilisateur non connecté.");
        }

      
    }

    
    


    @FXML
    private void handleShowPlaylist(ActionEvent event) {
        try {
            // Charger la vue de la playlist
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/playlist.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowFavorites(ActionEvent event) {
        try {
            // Charger la vue des favoris
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/favorite.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
