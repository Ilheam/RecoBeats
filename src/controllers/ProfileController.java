package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.User;
import services.UserService;
import app.Router;
import helpers.UserSession;

public class ProfileController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    private final UserService userService = new UserService();
    private User currentUser;

    @FXML
    public void initialize() {
        int currentUserId = UserSession.getInstance().getCurrentUserId();

        if (currentUserId > 0) {
            currentUser = userService.getUserProfile(currentUserId);

            if (currentUser != null) {
                firstNameField.setText(currentUser.getFirstName());
                lastNameField.setText(currentUser.getLastName());
            } else {
                showAlert(Alert.AlertType.ERROR, "Utilisateur non trouvé.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun utilisateur connecté.");
        }
    }

    @FXML
    private void handleUpdateProfile() {
    	System.out.println("User ID depuis session : " + currentUser);

        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Aucun utilisateur à mettre à jour.");
            return;
        }

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs.");
            return;
        }

        boolean isUpdated = userService.updateUserProfile(currentUser.getUserId(), firstName, lastName);

        if (isUpdated) {
            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            showAlert(Alert.AlertType.INFORMATION, "Profil mis à jour avec succès !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur lors de la mise à jour du profil.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	  @FXML
	    private void handleBackToUser() {
	        Router.navigateTo("/views/user.fxml");
	    }

}
