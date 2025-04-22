package controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import services.HistoryService;

public class HistoryController {

    @FXML
    private ListView<String> historyListView; // Liste pour afficher l'historique des chansons

    private HistoryService historyService = new HistoryService(); // Service pour gérer l'historique des écoutes

    // Méthode pour charger l'historique de l'utilisateur
    @FXML
    private void loadHistory() {
        int userId = 1; // Remplacer par la session de l'utilisateur connecté

        // Récupérer l'historique via le service
        List<String> history = historyService.getHistoryForUser(userId);
        
        if (history.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Aucune chanson dans l'historique.");
        }

        // Afficher l'historique dans la ListView
        historyListView.getItems().setAll(history);
    }

    // Méthode pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
