package models;

public enum Genre {
	POP, ROCK, HIPHOP, JAZZ, CLASSICAL, ELECTRONIC, BLUES, RNB, REGGAE, COUNTRY, METAL, FOLK, RAP, PUNK, LATIN, NEW_AGE,
	WORLD, OTHER;

	public static Genre fromString(String genreStr) {
		if (genreStr == null)
			return OTHER;

		switch (genreStr.trim().toLowerCase()) {
		case "pop":
			return POP;
		case "rock":
			return ROCK;
		case "hip hop":
		case "hip-hop":
		case "hiphop":
			return HIPHOP;
		case "jazz":
			return JAZZ;
		case "classical":
			return CLASSICAL;
		case "electronic":
			return ELECTRONIC;
		case "blues":
			return BLUES;
		case "r&b":
		case "rnb":
			return RNB;
		case "reggae":
			return REGGAE;
		case "country":
			return COUNTRY;
		case "metal":
			return METAL;
		case "folk":
			return FOLK;
		case "rap":
			return RAP;
		case "punk":
			return PUNK;
		case "latin":
			return LATIN;
		case "new age":
		case "new-age":
			return NEW_AGE;
		case "world":
			return WORLD;
		default:
			return OTHER;
		}
	}
}
