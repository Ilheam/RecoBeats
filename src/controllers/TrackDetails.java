package controllers;

import models.Track;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class TrackDetails {
    
    @FXML private TextField trackName;
    @FXML private TextField trackArtist;
    @FXML private TextField trackGenre;
    @FXML private Button playPreviewButton;

    private Track track;

    // Méthode pour définir la piste
    public void setTrack(Track track) {
        this.track = track;
        if (track != null) {
            trackName.setText(track.getName());
            trackArtist.setText(track.getArtist());
            trackGenre.setText(track.getGenre());
        } else {
            System.out.println("Track is null, cannot display details.");
        }
    }

    // Méthode pour jouer un aperçu de la chanson
    @FXML
    public void playPreview() {
        if (track != null) {
            System.out.println("Playing preview: " + track.getPreviewUrl());
            // Ajouter ici un lecteur audio si nécessaire
        } else {
            System.out.println("Track is null, cannot play preview.");
        }
    }
}
