package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router {

	private static Stage stage;

	public static void setStage(Stage stage) {
		Router.stage = stage;
	}

	// Méthode pour changer de vue
	public static void navigateTo(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(Router.class.getResource(fxml));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
