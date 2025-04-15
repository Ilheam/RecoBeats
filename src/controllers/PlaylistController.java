package controllers;



import models.Playlist;
import services.PlaylistService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PlaylistController {

    @FXML
    private ListView<Playlist> playlistListView;
    
    @FXML
    private TextField playlistNameField;
    
    private PlaylistService playlistService = new PlaylistService(); 
    
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    
    public void initialize() {// recup des playlists existantes
    	
        playlists.setAll(playlistService.getAllPlaylists());
        playlistListView.setItems(playlists);
    }
    
    @FXML
    public void addPlaylist() {
        String playlistName = playlistNameField.getText();
        if (!playlistName.isEmpty()) {
            Playlist newPlaylist = new Playlist(playlistName, 1); // Le 1 ici représente l'id de l'utilisateur, à remplacer dynamiquement.
            playlistService.addPlaylist(newPlaylist);
            playlists.add(newPlaylist);
            playlistNameField.clear();
        }
    }
    
    @FXML
    public void deletePlaylist() {
        Playlist selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {
            playlistService.deletePlaylist(selectedPlaylist.getId_playlist());
            playlists.remove(selectedPlaylist);
        }
    }
}

