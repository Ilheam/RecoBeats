<<<<<<< HEAD
package models;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

	private int id_playlist;
	private String name;
	private int user_id;
	private List<Song> songs;

	public Playlist(int id_playlist, String name, int user_id) {
		this.id_playlist = id_playlist;
		this.name = name;
		this.user_id = user_id;
		this.songs = new ArrayList<>();
	}

	// Constructeur sans id_playlist pour l'ajout de nouvelles playlists
	public Playlist(String name, int user_id) {
		this.name = name;
		this.user_id = user_id;
		this.songs = new ArrayList<>();
	}

	public int getId_playlist() {
		return id_playlist;
	}

	public void setId_playlist(int id_playlist) {
		this.id_playlist = id_playlist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public void addSong(Song song) {
		songs.add(song);
	}

	@Override
	public String toString() {
		return name;
	}

	public void removeSong(Song song) {
		songs.remove(song);
	}

=======

public class Playlist {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
