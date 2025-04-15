package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SongTrackView extends Application {

    @Override
    public void start(Stage primaryStage) {
    	try {
    		System.out.println(getClass().getResource("/views/song_details.fxml"));

    	    System.out.println("Chargement de l'interface FXML...");
    	    Parent root = FXMLLoader.load(getClass().getResource("/views/song_details.fxml"));
    	    System.out.println("FXML chargé avec succès !");

    	    Scene scene = new Scene(root, 800, 600);
    	    primaryStage.setTitle("RecoBeats 🎵");
    	    primaryStage.setScene(scene);
    	    primaryStage.show();
    	    System.out.println("Interface affichée !");
    	} catch (Exception e) {
    	    System.out.println("Erreur lors du chargement de l'interface FXML !");
    	    e.printStackTrace(); // INDISPENSABLE
    	}

    }

    public static void main(String[] args) {
        launch(args); // Lancement de l'application
    }
}
