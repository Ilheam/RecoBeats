package controllers;

import app.Router;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import models.User;
import services.UserService;

public class AdminDashboardController {

    // TableView pour les utilisateurs
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    private TableColumn<User, String> nomCol;
    @FXML
    private TableColumn<User, String> prenomCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, Void> actionCol;

    // Éléments du profil admin (admin.fxml)
    @FXML
    private Label profileInitialText;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Circle profileImageCircle;

    // Service utilisateur
    private final UserService userService = new UserService();

   
    @FXML
    public void initialize() {
        // Initialiser la table des utilisateurs si elle existe dans la vue actuelle
        if (userTable != null) {
            userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

            idCol.setCellValueFactory(data -> data.getValue().userIdProperty().asObject());
            nomCol.setCellValueFactory(data -> data.getValue().firstNameProperty());
            prenomCol.setCellValueFactory(data -> data.getValue().lastNameProperty());
            usernameCol.setCellValueFactory(data -> data.getValue().userNameProperty());

            addDeleteButtonToTable();
            loadUsers();
        }

        // Initialiser le profil admin si les éléments sont présents dans la vue
        if (firstNameLabel != null && usernameLabel != null && profileInitialText != null) {
            String adminName = "Admin";
            String adminUsername = "RecoBeats";

            firstNameLabel.setText(adminName);
            usernameLabel.setText(adminUsername);
            profileInitialText.setText(adminName.substring(0, 1).toUpperCase());
        }
    }

   
    private void loadUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList(userService.getAllUsers());
        userTable.setItems(userList);
    }

    
    private void addDeleteButtonToTable() {
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Supprimer");

            {
            	deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 5px;");
                deleteBtn.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    boolean deleted = userService.deleteUserByUsername(user.getUserName());
                    if (deleted) {
                        getTableView().getItems().remove(user);
                    } else {
                        showErrorMessage("Échec de la suppression de l'utilisateur.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });
    }

    
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    // Méthodes de navigation
    @FXML
    private void handleBackToLogin() {
        Router.navigateTo("/views/login.fxml");
    }

    @FXML
    private void handleBackToSongs() {
        Router.navigateTo("/views/dashsongs.fxml");
    }

    @FXML
    private void handleBackToUsers() {
        Router.navigateTo("/views/dashboard.fxml");
    }

    @FXML
    private void handleBackToAdmin() {
        Router.navigateTo("/views/admin.fxml");
    }
}
