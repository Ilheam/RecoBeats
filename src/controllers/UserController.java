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
import services.Session;

import java.io.IOException;
import java.util.List;

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

    @FXML private VBox sidebarMenu;  // Sidebar menu
    @FXML private Button hamburgerButton;  // Hamburger button
    @FXML private VBox sidebar; // Assure-toi d'ajouter fx:id="sidebar" à ton VBox dans le FXML

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

    @FXML
    private void handleDisplayUserHistory() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            List<String> history = userService.getUserHistory(userId);
            historyListView.getItems().setAll(history);
        } catch (NumberFormatException e) {
            System.out.println("ID utilisateur invalide.");
        }
    }

    @FXML
    private void handleUpdateUserHistory() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            String song = historySongField.getText();
            boolean success = userService.updateHistory(userId, song);
            System.out.println(success ? "Historique mis à jour." : "Échec de la mise à jour.");
        } catch (NumberFormatException e) {
            System.out.println("ID utilisateur invalide.");
        }
    }

    @FXML
    private void toggleMenu() {
        // Toggle the sidebar visibility
        sidebarMenu.setVisible(!sidebarMenu.isVisible());
    }

    @FXML
    private void goBack() {
        // Logic to go back to the previous page
        System.out.println("Going back to the previous page...");
    }

    // Fusion des deux méthodes initialize()
    @FXML
    public void initialize() {
        // Récupère l'utilisateur actuel depuis la session
        User currentUser = Session.getCurrentUser();
        
        if (currentUser != null) {
            // Affiche le prénom, nom et le username
            firstNameLabel.setText(currentUser.getFirstName());
            lastNameLabel.setText(currentUser.getLastName());
            usernameLabel.setText("@" + currentUser.getUserName());

            // Affiche la première lettre du prénom dans le cercle
            String firstLetter = currentUser.getUserName().substring(0, 1).toUpperCase();
            profileInitialText.setText(firstLetter);
        } else {
            System.out.println("Utilisateur non connecté.");
        }

        // Gestion des boutons de la sidebar
        for (var node : sidebar.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;

                // Appliquer le comportement au clic
                button.setOnAction(event -> {
                    clearSelection(); // Retirer la sélection précédente
                    button.getStyleClass().add("selected"); // Ajouter la classe sélectionnée
                });
            }
        }
    }

    // Enlève "selected" de tous les boutons
    private void clearSelection() {
        for (var node : sidebar.getChildren()) {
            if (node instanceof Button) {
                node.getStyleClass().remove("selected");
            }
        }
    }
}
