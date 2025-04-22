package models;

public class Favorite extends Playlist {
	
	public Favorite(int user_id) {
        super(0, "Favorites", user_id);  // on donne un id egal a 0 par defaut au favoris
    }
	
	public void addFavorite(Song song) {
        this.addSong(song);
    }
	
	public void removeFavorite(Song song) {
        this.removeSong(song);
    }

}
