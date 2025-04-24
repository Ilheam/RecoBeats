package models;

public class PlaylistTrack {
	private int id;
	private int playlistId;
	private int trackId;
	private int position;

	public PlaylistTrack(int id, int playlistId, int trackId, int position) {
		this.id = id;
		this.playlistId = playlistId;
		this.trackId = trackId;
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public int getPlaylistId() {
		return playlistId;
	}

	public int getTrackId() {
		return trackId;
	}

	public int getPosition() {
		return position;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}

	public void setPlaylistId(int playlistId) {
		this.playlistId = playlistId;
	}

	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "PlaylistTrack{" + "id=" + id + ", playlistId=" + playlistId + ", trackId=" + trackId + ", position="
				+ position + '}';
	}
}
