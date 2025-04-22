package models;

public class Preference {
    private int userId;
    private String genre, durationPref;
    private int preferredYear;
    private float energy, danceability, acousticness, instrumentalness, valence, speechiness;

    public Preference(int userId, String genre, int preferredYear, String durationPref,
                      float energy, float danceability, float acousticness,
                      float instrumentalness, float valence, float speechiness) {
        this.userId = userId;
        this.genre = genre;
        this.preferredYear = preferredYear;
        this.durationPref = durationPref;
        this.energy = energy;
        this.danceability = danceability;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.valence = valence;
        this.speechiness = speechiness;
    }

    
    // Getters & Setters 
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDurationPref() {
		return durationPref;
	}

	public void setDurationPref(String durationPref) {
		this.durationPref = durationPref;
	}

	public int getPreferredYear() {
		return preferredYear;
	}

	public void setPreferredYear(int preferredYear) {
		this.preferredYear = preferredYear;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public float getDanceability() {
		return danceability;
	}

	public void setDanceability(float danceability) {
		this.danceability = danceability;
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

	public float getValence() {
		return valence;
	}

	public void setValence(float valence) {
		this.valence = valence;
	}

	public float getSpeechiness() {
		return speechiness;
	}

	public void setSpeechiness(float speechiness) {
		this.speechiness = speechiness;
	}
   
	
	public double[] toVector() {
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


