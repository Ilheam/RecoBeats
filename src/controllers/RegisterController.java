<<<<<<< HEAD
package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.RegisterService;
import services.Session;
import services.UserService;
import app.Router;
import helpers.UserSession;

public class RegisterController {

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private TextField userNameField;

	@FXML
	private PasswordField passwordField;

	private RegisterService registerService = new RegisterService();

	@FXML
	private void handleRegister(ActionEvent event) {
		String nom = lastNameField.getText();
		String prenom = firstNameField.getText();
		String username = userNameField.getText();
		String password = passwordField.getText();

		if (prenom.isEmpty() || nom.isEmpty() || username.isEmpty() || password.isEmpty()) {
			showAlert("Champs manquants", "Veuillez remplir tous les champs.");
			return;
		}

		User newUser = new User(nom, prenom, username, password);

		UserService userService = new UserService();
		if (userService.addUser(newUser)) {
			User savedUser = userService.getUserByUsername(username);

			UserSession.getInstance().setCurrentUserId(savedUser.getUserId());

			try {
				goToProfile();
			} catch (Exception e) {
				e.printStackTrace();
				showAlert("Erreur", "Impossible de charger la page de profil !");
			}
		} else {
			showAlert("Erreur", "Échec de l'enregistrement. Veuillez réessayer.");
		}
	}

	private void goToProfile() {
		try {
			javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
					getClass().getResource("/views/PreferenceForm.fxml"));
			javafx.scene.Parent root = loader.load();
			javafx.stage.Stage stage = (javafx.stage.Stage) firstNameField.getScene().getWindow();
			stage.setScene(new javafx.scene.Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Erreur", "Impossible de charger la page de profil !");
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void clearFields() {
		firstNameField.clear();
		lastNameField.clear();
		userNameField.clear();
		passwordField.clear();
	}

	  @FXML
	    private void handleBackToWelcome() {
	        Router.navigateTo("/views/welcome.fxml");
	    }

=======

public class RegisterController {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
