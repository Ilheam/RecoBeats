<<<<<<< HEAD
package services;

import models.Admin;
import java.security.MessageDigest;

public class AdminService {

	private Admin defaultAdmin;

	public AdminService() {
		// On hash le mot de passe "admin123" en utilisant MessageDigest
		String hashedPassword = hashPassword("RecoBeats2025");
		this.defaultAdmin = new Admin("RecoBeats", hashedPassword);
	}

	public boolean authenticate(String username, String password) {
		String hashedInput = hashPassword(password);
		return defaultAdmin.getUsername().equals(username) && defaultAdmin.getPassword().equals(hashedInput);
	}

	// Méthode privée pour hacher un mot de passe en utilisant l'algorithme SHA-256
	private String hashPassword(String password) {
		try {
			// Création d'une instance de l'algorithme de hachage SHA-256
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// Application du hachage sur le mot de passe converti en tableau de bytes
			byte[] hash = md.digest(password.getBytes());

			// Utilisation d'un StringBuilder pour construire la chaîne hexadécimale
			StringBuilder sb = new StringBuilder();

			// Boucle sur chaque byte du tableau de hachage
			for (byte b : hash) {
				// Conversion de chaque byte en hexadécimal à deux chiffres
				sb.append(String.format("%02x", b));
			}

			// Retourne la chaîne hachée finale sous forme hexadécimale
			return sb.toString();
		} catch (Exception e) {
			// Si une erreur se produit (ex: SHA-256 non supporté), une exception est levée
			throw new RuntimeException("Erreur de hachage", e);
		}
	}

=======

public class AdminService {

>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
}
