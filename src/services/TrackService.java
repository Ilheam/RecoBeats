package services;

import models.Track;
import java.util.ArrayList;
import java.util.List;

public class TrackService {
    
    public static List<Track> getSampleTracks() {
        List<Track> tracks = new ArrayList<>();
        
        tracks.add(new Track(1, "Blinding Lights", "The Weeknd", "https://preview.mp3", "pop, energetic", "Pop", 2020, 200000, 0.85f, 0.72f, 5, -5.3f, 1, 0.06f, 0.1f, 0.0f, 0.12f, 0.8f, 120.0f, 4));
        tracks.add(new Track(2, "Levitating", "Dua Lipa", "https://preview.mp3", "dance, pop", "Pop", 2020, 203000, 0.87f, 0.75f, 6, -4.7f, 1, 0.08f, 0.05f, 0.0f, 0.15f, 0.9f, 103.0f, 4));
        tracks.add(new Track(3, "Shape of You", "Ed Sheeran", "https://preview.mp3", "romantic, pop", "Pop", 2017, 220000, 0.93f, 0.72f, 9, -3.8f, 1, 0.05f, 0.15f, 0.0f, 0.10f, 0.85f, 96.0f, 4));

        return tracks;
    }
}
