package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Preference;
import services.PreferenceService;

import javafx.event.ActionEvent;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


import controllers.RegisterController;

public class PreferenceController {

    @FXML private ComboBox<String> genreComboBox;
    @FXML private ComboBox<Integer> yearComboBox;

    @FXML private RadioButton courteRadio, normaleRadio, longueRadio;
    @FXML private ToggleGroup durationGroup;

    @FXML private Slider energySlider, danceabilitySlider, acousticnessSlider, instrumentalnessSlider, valenceSlider, speechinessSlider;

    @FXML private RadioButton vocalRadio, instrumentalRadio;
    @FXML private ToggleGroup vocalGroup;

    private int userId; // injecté depuis RegisterController

    public void setUserId(int id) {
        this.userId = id;
    }

    @FXML
    public void initialize() {
        genreComboBox.getItems().addAll("Pop", "Rock", "Jazz", "Hip-Hop", "Classique", "Électro",
        		"Blues","RnB","Reggae","Country","Metal","Folk","Rap","Punk","Latin","New age","World","Other");
        yearComboBox.getItems().addAll(1990, 2000, 2010, 2020);

        energySlider.setValue(0.5f);
        danceabilitySlider.setValue(0.5f);
        acousticnessSlider.setValue(0.5f);
        instrumentalnessSlider.setValue(0.5f);
        valenceSlider.setValue(0.5f);
        speechinessSlider.setValue(0.5f);
    }
    
    @FXML
    public void handleSubmitPreferences(ActionEvent event) {
        System.out.println("handleSubmitPreferences appelé !");
        handleSavePreferences(); //
        
        

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent homeRoot = loader.load();
            Scene homeScene = new Scene(homeRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(homeScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSavePreferences() {
        String genre = genreComboBox.getValue();
        Integer year = yearComboBox.getValue();
        String duration = courteRadio.isSelected() ? "courte" :
                          normaleRadio.isSelected() ? "normale" : "longue";

        Preference preference = new Preference(
                userId,
                genre,
                year,
                duration,
                (float) energySlider.getValue(),
                (float) danceabilitySlider.getValue(),
                (float) acousticnessSlider.getValue(),
                (float) instrumentalnessSlider.getValue(),
                (float) valenceSlider.getValue(),
                (float) speechinessSlider.getValue()
            );

        PreferenceService.savePreference(preference);

        // rediriger vers la page d'accueil ou dashboard
        
              
    }
    

}

