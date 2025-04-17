package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.User;
import services.UserService;

public class AdminDashboardController {

    // TableView pour afficher la liste des utilisateurs
    @FXML
    private TableView<User> userTable;

    // Colonnes pour le nom, prénom et nom d’utilisateur
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    
    private TableColumn<User, String> nomCol;
    @FXML
    private TableColumn<User, String> prenomCol;
    @FXML
    private TableColumn<User, String> usernameCol;

    // Colonne pour l’action (bouton Supprimer)
    @FXML
    private TableColumn<User, Void> actionCol;

    // Service pour les opérations liées aux utilisateurs
    private final UserService userService = new UserService();

    /**
     * Méthode d'initialisation
     */
    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés des objets User
    	/*
    	  CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN est le remplaçant recommandé qui
    	   conserve l’esprit de la politique précédente, en flexant surtout la dernière colonne.
    	 */
    	userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

    	// Liaison de la colonne "ID" à la propriété userId (type int → objet)
    	idCol.setCellValueFactory(data -> data.getValue().userIdProperty().asObject());

    	// Colonne "Nom" liée à la propriété firstName
    	nomCol.setCellValueFactory(data -> data.getValue().firstNameProperty());

    	// Colonne "Prénom" liée à la propriété lastName
    	prenomCol.setCellValueFactory(data -> data.getValue().lastNameProperty());

    	// Colonne "Nom d'utilisateur" liée à la propriété userName
    	usernameCol.setCellValueFactory(data -> data.getValue().userNameProperty());

        // Ajouter le bouton "Supprimer" dans la table
        addDeleteButtonToTable();

        // Charger la liste des utilisateurs depuis la base de données
        loadUsers();
    }

    /**
     * Charger les utilisateurs depuis la base de données.
     */
    private void loadUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList(userService.getAllUsers());
        userTable.setItems(userList);
    }

    /**
     * Ajouter un bouton "Supprimer" dans chaque ligne de la TableView.
     */
    private void addDeleteButtonToTable() {
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Supprimer");

            {
                // Action au clic sur le bouton de suppression
                deleteBtn.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());

                    // Supprimer l'utilisateur via le service
                    boolean deleted = userService.deleteUserByUsername(user.getUserName());

                    // Si la suppression réussit, retirer l'utilisateur de la TableView
                    if (deleted) {
                        getTableView().getItems().remove(user);
                    } 
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                // Afficher le bouton uniquement si la ligne contient un utilisateur
                setGraphic(empty ? null : deleteBtn);
            }
        });
    }

    // Afficher un message d'erreur
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
