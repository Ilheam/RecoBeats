package services;

import database.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterService {

    // Méthode pour enregistrer un utilisateur dans la base de données
	public boolean registerUser(String firstName, String lastName, String username, String password) {
	    try (Connection conn = DBConnection.getConnection()) {

	        // Vérifier si l'utilisateur existe déjà
	        String checkQuery = "SELECT * FROM users WHERE username = ?";
	        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
	            checkStmt.setString(1, username);
	            ResultSet rs = checkStmt.executeQuery();

	            if (rs.next()) {
	                return false; // Nom d'utilisateur déjà utilisé
	            }
	        }

	        // Hashage du mot de passe
	        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

	        // Insertion dans la base de données
	        String insertQuery = "INSERT INTO users (nom, prenom, username, passwd) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	            insertStmt.setString(1, firstName);
	            insertStmt.setString(2, lastName);
	            insertStmt.setString(3, username);
	            insertStmt.setString(4, hashedPassword); // mot de passe haché
	            insertStmt.executeUpdate();
	        }

	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
