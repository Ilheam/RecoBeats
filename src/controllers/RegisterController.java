package controllers;

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
import services.RegisterService;
import services.Session;
import services.UserService;

public class RegisterController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    private RegisterService registerService = new RegisterService();

    @FXML
    private void handleRegister(ActionEvent event) {
        String nom = lastNameField.getText();
        String prenom = firstNameField.getText();
        String username = userNameField.getText(); 
        String password = passwordField.getText();

        if (prenom.isEmpty() || nom.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }

        User newUser = new User(nom, prenom, username, password);
        UserService userService = new UserService();

        if (userService.addUser(newUser)) {
            User savedUser = userService.getUserByUsername(username);
            Session.setCurrentUser(savedUser);

            try {
                goToPreferenceForm(savedUser);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible de charger le formulaire de préférences !");
            }
        } else {
            showAlert("Erreur", "Échec de l'enregistrement. Veuillez réessayer.");
        }
    }

    private void goToPreferenceForm(User user) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PreferenceForm.fxml"));
        Parent root = loader.load();

        PreferenceController controller = loader.getController();
        controller.setUserId(user.getUserId()); // envoie l'ID au contrôleur de préférences

        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        userNameField.clear();
        passwordField.clear();
    }
}
