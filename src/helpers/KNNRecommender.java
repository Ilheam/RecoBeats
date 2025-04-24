package helpers;

import models.Song;
import java.util.ArrayList;
import java.util.List;

public class KNNRecommender {

	// Méthode pour obtenir les morceaux similaires à une chanson donnée
	public List<Song> findSimilarTracks(Song currentSong, List<Song> allSongs, int k) {
		List<Song> similarSongs = new ArrayList<>();
		List<SimilarityScore> scores = new ArrayList<>();

		for (Song song : allSongs) {
			if (song.getTrack_id() != currentSong.getTrack_id()) { 
				double similarity = CosineSimilarity.calculate(currentSong, song);

				
				double weightedSimilarity = (0.5 * similarity) + (0.3 * similarity) + (0.2 * similarity);

				scores.add(new SimilarityScore(song, weightedSimilarity));
			}
		}

		// Trier par similarité décroissante
		scores.sort((score1, score2) -> Double.compare(score2.getSimilarity(), score1.getSimilarity()));

		// Ajouter les k morceaux les plus similaires
		for (int i = 0; i < k && i < scores.size(); i++) {
			similarSongs.add(scores.get(i).getSong());
		}

		return similarSongs;
	}


	// Classe interne pour stocker la chanson et sa similarité
	private static class SimilarityScore {
		private Song song;
		private double similarity;

		public SimilarityScore(Song song, double similarity) {
			this.song = song;
			this.similarity = similarity;
		}

		public Song getSong() {
			return song;
		}

		public double getSimilarity() {
			return similarity;
		}
	}
}
