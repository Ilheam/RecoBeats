package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Historique;
import services.HistoriqueService;
import services.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoriqueController implements Initializable {

    @FXML
    private TableView<Historique> tableViewHistorique;

    @FXML
    private TableColumn<Historique, String> colNom;

    @FXML
    private TableColumn<Historique, String> colArtiste;

    @FXML
    private TableColumn<Historique, Integer> colPlaycount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNom.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(
                () -> data.getValue().getSong().getTrack_name()));
        colArtiste.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(
                () -> data.getValue().getSong().getArtist_name()));
        colPlaycount.setCellValueFactory(new PropertyValueFactory<>("playcount"));

        loadHistorique();
    }

    private void loadHistorique() {
        HistoriqueService historiqueService = new HistoriqueService();
        List<Historique> historiqueList = historiqueService.getHistoriqueForUser(Session.getCurrentUser().getUserId());
        tableViewHistorique.getItems().setAll(historiqueList);
    }
}

