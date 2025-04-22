package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.User;
import services.Session;
import services.UserService;
import org.mindrot.jbcrypt.BCrypt; 

public class ProfileController {

    @FXML
    private TextField firstNameField; // Champ pour le prénom
    @FXML
    private TextField lastNameField;  // Champ pour le nom
    @FXML
    private TextField userNameField;   // Champ pour le nom d'utilisateur
    @FXML
    private PasswordField passwordField; // Champ pour le mot de passe

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        // Récupérer l'utilisateur actuel de la session et préremplir les champs
        User currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            firstNameField.setText(currentUser.getFirstName());
            lastNameField.setText(currentUser.getLastName());
            userNameField.setText(currentUser.getUserName());
        }
    }

   
    @FXML
    private void handleUpdateProfile() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = userNameField.getText();
      
        // Vérification que tous les champs nécessaires sont remplis
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs !");
            return;
        }

        User currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            // On met à jour seulement le prénom, le nom et le nom d'utilisateur, en conservant le mot de passe actuel
            boolean isUpdated = userService.updateUserProfile(
                currentUser.getUserId(), firstName, lastName, username, currentUser.getPassword()
            );

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Profil mis à jour avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur lors de la mise à jour du profil.");
            }
        }
    }



    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
