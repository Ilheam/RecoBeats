package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AdminService;

public class LoginAdminController {

	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;

	private final AdminService adminService = new AdminService();

	@FXML
	private void handleLogin() {
		System.out.println("connexion réussite");
	}
}
