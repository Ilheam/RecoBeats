package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WelcomeController {

	@FXML
	private ImageView logoImage;
	@FXML
	private Button loginButton;
	@FXML
	private Button registerButton;
	@FXML
	private VBox vboxButtons;

	@FXML
	public void initialize() {
		// Image circulaire
		Circle clip = new Circle(100, 100, 100); // x, y, rayon
		logoImage.setClip(clip);

		// Animations
		playAnimations();
	}

	private void playAnimations() {
		// Animation logo : fade + zoom
		FadeTransition fadeLogo = new FadeTransition(Duration.seconds(1), logoImage);
		fadeLogo.setFromValue(0);
		fadeLogo.setToValue(1);

		ScaleTransition scaleLogo = new ScaleTransition(Duration.seconds(1), logoImage);
		scaleLogo.setFromX(0.5);
		scaleLogo.setFromY(0.5);
		scaleLogo.setToX(1);
		scaleLogo.setToY(1);

		// Animation boutons : fade
		FadeTransition fadeButtons = new FadeTransition(Duration.seconds(1), vboxButtons);
		fadeButtons.setFromValue(0);
		fadeButtons.setToValue(1);

		// Enchaîner les animations
		SequentialTransition sequence = new SequentialTransition(fadeLogo, scaleLogo, fadeButtons);
		sequence.play();
	}

	// Méthode pour passer à l'écran de login
	@FXML
	public void handleLogin(ActionEvent event) {
		try {
			// Charger l'écran Login
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
			Parent root = loader.load();
			System.out.println("Login button clicked");
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Méthode pour passer à l'écran d'inscription
	@FXML
	public void handleRegister(ActionEvent event) {
		try {
			// Charger l'écran Register
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/register.fxml"));
			Parent root = loader.load();
			System.out.println("Register button clicked");
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}