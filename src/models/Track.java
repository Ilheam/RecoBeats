package models;

public class Track {
	
	private int trackId;
	private String name;
	private String artist;
	private String previewUrl;
	private String tags;
	private String genre;
	private int year;
	private int durationMs;
	private float danceability;
	private float energy;
	private int key;
	private float loudness;
	private int mode;
	private float speechiness;
	private float acousticness;
	private float instrumentalness;
	private float liveness;
	private float valence;
	private float tempo;
	private int timeSignature;
	
	
	public Track(int trackId, String name, String artist, String previewUrl, String tags, String genre, int year, int durationMs, float danceability, float energy, int key, float loudness, int mode, float speechiness, float acousticness, float instrumentalness, float liveness, float valence, float tempo, int timeSignature) {
		this.trackId=trackId;
		this.name=name;
		this.artist = artist;
        this.previewUrl = previewUrl;
        this.tags = tags;
        this.genre = genre;
        this.year = year;
        this.durationMs = durationMs;
        this.danceability = danceability;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.timeSignature = timeSignature;
	}


	public int getTrackId() {
		return trackId;
	}


	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getArtist() {
		return artist;
	}


	public void setArtist(String artist) {
		this.artist = artist;
	}


	public String getPreviewUrl() {
		return previewUrl;
	}


	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getDurationMs() {
		return durationMs;
	}


	public void setDurationMs(int durationMs) {
		this.durationMs = durationMs;
	}


	public float getDanceability() {
		return danceability;
	}


	public void setDanceability(float danceability) {
		this.danceability = danceability;
	}


	public float getEnergy() {
		return energy;
	}


	public void setEnergy(float energy) {
		this.energy = energy;
	}


	public int getKey() {
		return key;
	}


	public void setKey(int key) {
		this.key = key;
	}


	public float getLoudness() {
		return loudness;
	}


	public void setLoudness(float loudness) {
		this.loudness = loudness;
	}


	public int getMode() {
		return mode;
	}


	public void setMode(int mode) {
		this.mode = mode;
	}


	public float getSpeechiness() {
		return speechiness;
	}


	public void setSpeechiness(float speechiness) {
		this.speechiness = speechiness;
	}


	public float getAcousticness() {
		return acousticness;
	}


	public void setAcousticness(float acousticness) {
		this.acousticness = acousticness;
	}


	public float getInstrumentalness() {
		return instrumentalness;
	}


	public void setInstrumentalness(float instrumentalness) {
		this.instrumentalness = instrumentalness;
	}


	public float getLiveness() {
		return liveness;
	}


	public void setLiveness(float liveness) {
		this.liveness = liveness;
	}


	public float getValence() {
		return valence;
	}


	public void setValence(float valence) {
		this.valence = valence;
	}


	public float getTempo() {
		return tempo;
	}


	public void setTempo(float tempo) {
		this.tempo = tempo;
	}


	public int getTimeSignature() {
		return timeSignature;
	}


	public void setTimeSignature(int timeSignature) {
		this.timeSignature = timeSignature;
	}


	@Override
	public String toString() {
		return "Track [trackId=" + trackId + ", name=" + name + ", artist=" + artist + ", previewUrl=" + previewUrl
				+ ", tags=" + tags + ", genre=" + genre + ", year=" + year + ", durationMs=" + durationMs
				+ ", danceability=" + danceability + ", energy=" + energy + ", key=" + key + ", loudness=" + loudness
				+ ", mode=" + mode + ", speechiness=" + speechiness + ", acousticness=" + acousticness
				+ ", instrumentalness=" + instrumentalness + ", liveness=" + liveness + ", valence=" + valence
				+ ", tempo=" + tempo + ", timeSignature=" + timeSignature + "]";
	}
	
	
	
	

}
