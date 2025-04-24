package models;

public class UserPreferences {
	private int userId;
	private float danceability;
	private float energy;
	private float speechiness;
	private float acousticness;
	private float instrumentalness;
	private float valence;

	public UserPreferences(int userId, float danceability, float energy, float speechiness, float acousticness,
			float instrumentalness, float valence) {
		this.userId = userId;
		this.danceability = danceability;
		this.energy = energy;
		this.speechiness = speechiness;
		this.acousticness = acousticness;
		this.instrumentalness = instrumentalness;
		this.valence = valence;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public float getValence() {
		return valence;
	}

	public void setValence(float valence) {
		this.valence = valence;
	}

	public float[] getVector() {
		return new float[] { danceability, energy, speechiness, acousticness, instrumentalness, valence };
	}
}
