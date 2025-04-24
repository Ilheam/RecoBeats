package services;

import database.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

	// Méthode pour authentifier un utilisateur
	public boolean authenticateUser(String userName, String password) {

		try (Connection conn = DBConnection.getConnection()) { // Établir la connexion à la base de données
			if (conn == null) {

				return false;
			}

			// Récupérer le mot de passe hashé depuis la base
			String query = "SELECT passwd FROM users WHERE username = ?"; // Requête SQL pour obtenir le mot de passe
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, userName); // Définir le nom d'utilisateur dans la requête
				ResultSet rs = stmt.executeQuery(); // Exécuter la requête

				if (rs.next()) { // Vérifier si un résultat a été retourné
					String hashedPassword = rs.getString("passwd"); // Récupérer le mot de passe hashé

					// Vérifier si le mot de passe fourni correspond au mot de passe hashé
					boolean passwordMatches = BCrypt.checkpw(password, hashedPassword);

					return passwordMatches; // Retourner le résultat de la vérification
				} else {

					return false; // Utilisateur non trouvé dans la base de données
				}
			}
		} catch (SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage()); // Impression de l'erreur SQL
			return false; // Retourner false en cas d'erreur SQL
		}
	}
}
