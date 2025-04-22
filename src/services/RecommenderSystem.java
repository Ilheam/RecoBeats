package services;

import java.util.List;
import java.util.ArrayList;

import models.*;
import controllers.CosineSimilarity;

public class RecommenderSystem {
    private List<Song> songs;
    private List<UserPreferences> userPreferences;
    private List<Favorite> favorites;

    public RecommenderSystem(List<Song> songs, List<UserPreferences> userPreferences, List<Favorite> favorites) {
        this.songs = songs;
        this.userPreferences = userPreferences;
        this.favorites = favorites;
    }

    public List<Song> recommendTracks(int userId, int clickedTrackId) {
        Song clickedTrack = getSongById(clickedTrackId);
        if (clickedTrack == null) return new ArrayList<>();

        UserPreferences userPref = getUserPreferences(userId);
        if (userPref == null) return new ArrayList<>();

        double[] clickedTrackFeatures = extractFeatures(clickedTrack);
        double[] userPrefFeatures = extractFeatures(userPref);
        double[] avgFavorites = getAverageFavorites(userId);

        double[] userProfile = combineProfiles(clickedTrackFeatures, userPrefFeatures, avgFavorites);

        List<Song> recommendedTracks = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTrack_id() != clickedTrackId) {
                double similarity = CosineSimilarity.calculate(userProfile, extractFeatures(song));
                song.setSimilarity(similarity); // Tu dois avoir un setter dans la classe Song
                recommendedTracks.add(song);
            }
        }

        recommendedTracks.sort((s1, s2) -> Double.compare(s2.getSimilarity(), s1.getSimilarity()));

        return recommendedTracks.size() > 10 ? recommendedTracks.subList(0, 10) : recommendedTracks;
    }

    private double[] extractFeatures(Song song) {
        return new double[] {
            song.getDanceability(),
            song.getEnergy(),
            song.getAcousticness(),
            song.getInstrumentalness(),
            song.getValence(),
            song.getSpeechiness()
        };
    }

    private double[] extractFeatures(UserPreferences pref) {
        return new double[] {
            pref.getDanceability(),
            pref.getEnergy(),
            pref.getAcousticness(),
            pref.getInstrumentalness(),
            pref.getValence(),
            pref.getSpeechiness()
        };
    }

    private double[] combineProfiles(double[] clickedTrack, double[] userPref, double[] avgFavorites) {
        double[] profile = new double[clickedTrack.length];
        for (int i = 0; i < profile.length; i++) {
            profile[i] = 0.5 * clickedTrack[i] + 0.3 * userPref[i] + 0.2 * avgFavorites[i];
        }
        return profile;
    }

    private double[] getAverageFavorites(int userId) {
        List<Song> favoriteSongs = getFavoriteSongs(userId);
        double[] avg = new double[6];

        if (favoriteSongs.isEmpty()) return avg;

        for (Song song : favoriteSongs) {
            double[] features = extractFeatures(song);
            for (int i = 0; i < avg.length; i++) {
                avg[i] += features[i];
            }
        }

        for (int i = 0; i < avg.length; i++) {
            avg[i] /= favoriteSongs.size();
        }

        return avg;
    }

    private List<Song> getFavoriteSongs(int userId) {
        for (Favorite fav : favorites) {
            if (fav.getUser_id() == userId) {
                return fav.getSongs();
            }
        }
        return new ArrayList<>();
    }

    private Song getSongById(int trackId) {
        for (Song song : songs) {
            if (song.getTrack_id() == trackId) {
                return song;
            }
        }
        return null;
    }

    private UserPreferences getUserPreferences(int userId) {
        for (UserPreferences pref : userPreferences) {
            if (pref.getUserId() == userId) {
                return pref;
            }
        }
        return null;
    }
}
