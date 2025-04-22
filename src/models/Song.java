package models;


public class Song {
	
	private int track_id;
    private String track_name;
    private String artist_name;
    private String preview_url;
    private String tags;
    private Genre genre; 
    private int year;
    private int duration_ms;
    private float danceability;
    private float energy;
    private int key_mode;
    private float loudness;
    private String mode;
    private float speechiness;
    private float acousticness;
    private float instrumentalness;
    private float liveness;
    private float valence;
    private float tempo;
    private int time_signature;
    private String img_url;
    
    private double similarity;
    
    
    
	public Song(int track_id, String track_name, String artist_name, String preview_url, String tags, Genre genre,
			int year, int duration_ms, float danceability, float energy, int key_mode, float loudness, String mode,
			float speechiness, float acousticness, float instrumentalness, float liveness, float valence, float tempo,
			int time_signature, String img_url) {
		this.track_id = track_id;
		this.track_name = track_name;
		this.artist_name = artist_name;
		this.preview_url = preview_url;
		this.tags = tags;
		this.genre = genre;
		this.year = year;
		this.duration_ms = duration_ms;
		this.danceability = danceability;
		this.energy = energy;
		this.key_mode = key_mode;
		this.loudness = loudness;
		this.mode = mode;
		this.speechiness = speechiness;
		this.acousticness = acousticness;
		this.instrumentalness = instrumentalness;
		this.liveness = liveness;
		this.valence = valence;
		this.tempo = tempo;
		this.time_signature = time_signature;
		this.img_url = img_url;
	}
	
	
	public void setSimilarity(double similarity) {
	    this.similarity = similarity;
	}

	public double getSimilarity() {
	    return similarity;
	}
	
	public int getTrack_id() {
		return track_id;
	}
	
	
	public void setTrack_id(int track_id) {
		this.track_id = track_id;
	}
	
	
	public String getTrack_name() {
		return track_name;
	}
	
	
	public void setTrack_name(String track_name) {
		this.track_name = track_name;
	}
	
	
	public String getArtist_name() {
		return artist_name;
	}
	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}
	public String getPreview_url() {
		return preview_url;
	}
	public void setPreview_url(String preview_url) {
		this.preview_url = preview_url;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getDuration_ms() {
		return duration_ms;
	}
	public void setDuration_ms(int duration_ms) {
		this.duration_ms = duration_ms;
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
	public int getKey_mode() {
		return key_mode;
	}
	public void setKey_mode(int key_mode) {
		this.key_mode = key_mode;
	}
	public float getLoudness() {
		return loudness;
	}
	public void setLoudness(float loudness) {
		this.loudness = loudness;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
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
	public int getTime_signature() {
		return time_signature;
	}
	public void setTime_signature(int time_signature) {
		this.time_signature = time_signature;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
    
	@Override
    public String toString() {
        return track_name + " : " + artist_name;
    }
	
	public double[] getFeatureVector() {
        return new double[] {
            danceability,
            energy,
            acousticness,
            instrumentalness,
            valence,
            speechiness
        };
    }

}

