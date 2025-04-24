package controllers;

<<<<<<< HEAD
import java.io.IOException;

import app.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User; 
import services.LoginService;
import services.UserService; 
import services.Session;
import helpers.UserSession;

public class LoginController {

	@FXML
	private TextField usernameField; // Champ pour le nom d'utilisateur

	@FXML
	private PasswordField passwordField; // Champ pour le mot de passe

	private LoginService loginService = new LoginService(); // Service d'authentification
	private UserService userService = new UserService(); // Service pour récupérer les détails de l'utilisateur

	@FXML
	private void handleLogin(ActionEvent event) {
		String username = usernameField.getText();
		String password = passwordField.getText();

		if (username.isEmpty() || password.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs.");
			return;
		}

		// Cas spécial pour RecoBeats sans passer par LoginService(admin)
		if (username.equals("recobeats") && password.equals("recobeats2025")) {
			// Création manuelle de l'utilisateur fictif
			User user = new User(0, "admin", "admin", "recobeats", "recobeats2025");
			user.setUserId(0); // Tu peux mettre n’importe quelle valeur
			user.setUserName("RecoBeats");

			UserSession.getInstance().setCurrentUserId(user.getUserId());

			try {
				Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/views/admin.fxml"));
				Stage stage = (Stage) usernameField.getScene().getWindow();
				stage.setScene(new Scene(dashboardRoot));
			} catch (IOException e) {
				e.printStackTrace();
				showAlert(Alert.AlertType.ERROR, "Impossible de charger la page.");
			}
			return; // Important pour ne pas continuer plus loin
		}

		// Authentification normale avec les services
		boolean isAuthenticated = loginService.authenticateUser(username, password);

		if (isAuthenticated) {
			User user = userService.getUserByUsername(username);

			if (user != null) {
				UserSession.getInstance().setCurrentUserId(user.getUserId());

				try {
					Parent homeRoot = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
					Stage stage = (Stage) usernameField.getScene().getWindow();
					stage.setScene(new Scene(homeRoot));
				} catch (IOException e) {
					e.printStackTrace();
					showAlert(Alert.AlertType.ERROR, "Impossible de charger l'écran d'accueil.");
				}
			} else {
				showAlert(Alert.AlertType.ERROR, "Utilisateur introuvable.");
			}
		} else {
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
	
	@FXML
    private void handleBackToWelcome() {
        Router.navigateTo("/views/welcome.fxml");
    }
=======
public class LoginController {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
