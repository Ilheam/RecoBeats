package helpers;

import models.Song;
import java.util.List;

public class CosineSimilarity {

	// Méthode pour calculer la similarité cosinus entre deux morceaux
	public static double calculate(Song song1, Song song2) {
		double[] song1Features = { song1.getDanceability(), song1.getEnergy(), song1.getLoudness(),
				song1.getSpeechiness(), song1.getAcousticness(), song1.getInstrumentalness(), song1.getLiveness(),
				song1.getValence(), song1.getTempo(), song1.getDuration_ms() };

		double[] song2Features = { song2.getDanceability(), song2.getEnergy(), song2.getLoudness(),
				song2.getSpeechiness(), song2.getAcousticness(), song2.getInstrumentalness(), song2.getLiveness(),
				song2.getValence(), song2.getTempo(), song2.getDuration_ms() };

		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		for (int i = 0; i < song1Features.length; i++) {
			dotProduct += song1Features[i] * song2Features[i];
			normA += Math.pow(song1Features[i], 2);
			normB += Math.pow(song2Features[i], 2);
		}

		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
}
