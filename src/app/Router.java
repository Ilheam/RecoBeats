package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Router {
    public static Stage stage; // On suppose qu'elle sera initialisée au démarrage de l'application

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
