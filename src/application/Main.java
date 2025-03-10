package application;

import controllers.TrackDetails;
import models.Track;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Track_Details.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur et définir une piste manuellement
            TrackDetails controller = loader.getController();
            Track sampleTrack = new Track(1, "Sample Song", "Sample Artist", "http://example.com/preview", "pop, chill", "Pop", 2020, 300000, 0.8f, 0.7f, 5, -5.0f, 1, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 120.0f, 4);
            controller.setTrack(sampleTrack);  // Définir une piste manuellement

            // Configurer la scène et afficher l'application
            primaryStage.setTitle("Recobeats - Music Recommendation System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
