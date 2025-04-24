package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import helpers.UserSession;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialiser le Router avec le stage principal
		Router.setStage(primaryStage);

		UserSession.getInstance().setCurrentUserId(4);

		// Charger la vue home.hfxml 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/welcome.fxml"));
		Parent root = loader.load();
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/RECOBEATS.png")));

		primaryStage.setTitle("RecoBeats");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
