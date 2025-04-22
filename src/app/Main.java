package app;

import controllers.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
    	Parent root = loader.load();
    	Stage stage = new Stage();
    	stage.setScene(new Scene(root));
    	stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
