package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User; // Assurez-vous d'importer le modèle User
import services.LoginService;
import services.UserService; // Assurez-vous d'importer le service UserService
import services.Session; // Assurez-vous d'importer le service Session

public class LoginController {

    @FXML
    private TextField usernameField; // Champ pour le nom d'utilisateur

    @FXML
    private PasswordField passwordField; // Champ pour le mot de passe

    private LoginService loginService = new LoginService(); // Service d'authentification
    private UserService userService = new UserService(); // Service pour récupérer les détails de l'utilisateur

    @FXML
    private void handleLogin(ActionEvent event) {
        // Récupération des valeurs saisies par l'utilisateur
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Vérification que les champs ne sont pas vides
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs !");
            return; // Sortie de la méthode si les champs sont vides
        }

        // Authentification de l'utilisateur
        boolean isAuthenticated = loginService.authenticateUser(username, password);

        // Vérification du résultat de l'authentification
        if (isAuthenticated) {
            // Récupérer l'utilisateur après authentification
            User user = userService.getUserByUsername(username); // Assurez-vous que cette méthode existe
            if (user != null) {
                Session.setCurrentUser(user); // Mettre à jour la session avec l'utilisateur connecté
                try {
                    // Chargement de l'écran de profil
                    Parent profileRoot = FXMLLoader.load(getClass().getResource("/views/user.fxml"));
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(new Scene(profileRoot));
                } catch (IOException e) {
                    e.printStackTrace(); // Affichage de l'erreur dans la console
                    showAlert(Alert.AlertType.ERROR, "Impossible de charger l'écran de profil.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur lors de la récupération des informations de l'utilisateur.");
            }
        } else {
            // Alerte en cas d'échec de l'authentification
            showAlert(Alert.AlertType.ERROR, "Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        try {
            // Chargement de l'écran d'inscription
            Parent registerRoot = FXMLLoader.load(getClass().getResource("/views/Register.fxml")); 
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(registerRoot));
        } catch (IOException e) {
            e.printStackTrace(); // Affichage de l'erreur dans la console
            showAlert(Alert.AlertType.ERROR, "Impossible de charger l'écran d'inscription.");
        }
    }

    // Méthode pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
