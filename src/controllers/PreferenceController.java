package controllers;

import app.Router;
import helpers.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import models.Preference;
import services.PreferenceService;

public class PreferenceController {

	@FXML
	private ComboBox<String> genreComboBox;
	@FXML
	private ComboBox<Integer> yearComboBox;

	@FXML
	private RadioButton courteRadio, normaleRadio, longueRadio;
	@FXML
	private ToggleGroup durationGroup;

	@FXML
	private Slider energySlider, danceabilitySlider, acousticnessSlider, instrumentalnessSlider, valenceSlider,
			speechinessSlider;

	@FXML
	private RadioButton vocalRadio, instrumentalRadio;
	@FXML
	private ToggleGroup vocalGroup;

	@FXML
	public void initialize() {
		genreComboBox.getItems().addAll("Pop", "Rock", "Jazz", "Hip-Hop", "Classique", "Électro", "Blues", "RnB",
				"Reggae", "Country", "Metal", "Folk", "Rap", "Punk", "Latin", "New age", "World", "Other");
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
		handleSavePreferences();

		Router.navigateTo("/views/home.fxml");
	}

	@FXML
	private void handleSavePreferences() {
		String genre = genreComboBox.getValue();
		Integer year = yearComboBox.getValue();
		String duration = courteRadio.isSelected() ? "courte" : normaleRadio.isSelected() ? "normale" : "longue";

		int userId = UserSession.getInstance().getCurrentUserId();

		if (userId == 0) {
			showAlert(Alert.AlertType.ERROR, "Aucun utilisateur connecté !");
			return;
		}

		Preference preference = new Preference(userId, genre, year, duration, (float) energySlider.getValue(),
				(float) danceabilitySlider.getValue(), (float) acousticnessSlider.getValue(),
				(float) instrumentalnessSlider.getValue(), (float) valenceSlider.getValue(),
				(float) speechinessSlider.getValue());

		PreferenceService.savePreference(preference);
		showAlert(Alert.AlertType.INFORMATION, "Préférences enregistrées avec succès !");
	}

	private void showAlert(Alert.AlertType type, String message) {
		Alert alert = new Alert(type);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
