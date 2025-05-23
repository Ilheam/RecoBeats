<<<<<<< HEAD
package services;

import models.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

	public boolean addUser(User user) {
		String query = "INSERT INTO users (nom, prenom, username, passwd) VALUES (?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			// Hashing the password before saving
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

			statement.setString(1, user.getLastName()); // nom
			statement.setString(2, user.getFirstName()); // prenom
			statement.setString(3, user.getUserName()); // username
			statement.setString(4, hashedPassword); // passwd

			int rows = statement.executeUpdate();

			return rows > 0;

		} catch (SQLException e) {
			System.err.println("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public User getUserByUsername(String username) {
		String query = "SELECT * FROM users WHERE username = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, username);

			// Exécuter la requête et récupérer les résultats
			ResultSet rs = stmt.executeQuery();

			// Si un utilisateur est trouvé, récupérer ses informations
			if (rs.next()) {
				int userId = rs.getInt("id_user");
				String prenom = rs.getString("prenom");
				String nom = rs.getString("nom");
				String dbUsername = rs.getString("username");
				String passwd = rs.getString("passwd");

				// affich pour vérifier que les informations récupérées sont correctes
				System.out.println("Utilisateur trouvé : ");
				System.out.println("ID : " + userId);
				System.out.println("Prénom : " + prenom);
				System.out.println("Nom : " + nom);
				System.out.println("Username : " + dbUsername);

				// Retourner l'utilisateur
				return new User(userId, prenom, nom, dbUsername, passwd);
			} else {
				// Si aucun utilisateur n'est trouvé
				System.out.println("Aucun utilisateur trouvé avec le nom d'utilisateur : " + username);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Afficher l'exception si la requête échoue
		}
		return null;
	}

	public User getUserById(int userId) {
		String query = "SELECT * FROM users WHERE id_user = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new User(rs.getInt("id_user"), rs.getString("prenom"), rs.getString("nom"),
						rs.getString("username"), rs.getString("passwd"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Récupérer le profil de base (infos personnelles uniquement)
	public User getUserProfile(int userId) {
		String query = "SELECT * FROM users WHERE id_user = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String firstName = resultSet.getString("nom");
				String lastName = resultSet.getString("prenom");
				String userName = resultSet.getString("username");
				String password = resultSet.getString("passwd");

				return new User(userId, firstName, lastName, userName, password);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean updateUserProfile(int userId, String firstName, String lastName) {
        String query = "UPDATE users SET nom = ?, prenom = ? WHERE id_user = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, userId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Mise à jour réussie de l'utilisateur avec ID: " + userId);
                return true;
            } else {
                System.out.println("Aucune ligne mise à jour pour l'utilisateur avec ID: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


	// Obtenir la playlist d’un utilisateur
	public List<String> getUserPlaylist(int userId) {
		List<String> playlist = new ArrayList<>();
		String query = "SELECT song_name FROM user_playlist WHERE id_user = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				playlist.add(resultSet.getString("song_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return playlist;
	}

	// Ajouter ou supprimer une chanson dans la playlist
	public boolean updatePlaylist(int userId, String song, boolean add) {
		String query = add ? "INSERT INTO user_playlist (user_id, song_name) VALUES (?, ?)"
				: "DELETE FROM user_playlist WHERE user_id = ? AND song_name = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			statement.setString(2, song);

			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Obtenir les favoris
	public List<String> getUserFavorites(int userId) {
		List<String> favorites = new ArrayList<>();
		String query = "SELECT song_name FROM user_favorites WHERE user_id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				favorites.add(resultSet.getString("song_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return favorites;
	}

	// Ajouter ou supprimer une chanson des favoris
	public boolean updateFavorites(int userId, String song, boolean add) {
		String query = add ? "INSERT INTO user_favorites (user_id, song_name) VALUES (?, ?)"
				: "DELETE FROM user_favorites WHERE user_id = ? AND song_name = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, userId);
			statement.setString(2, song);

			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	
	

	// Récupérer tous les utilisateurs
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				int id = resultSet.getInt("id_user");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String username = resultSet.getString("username");
				String passwd = resultSet.getString("passwd");

				users.add(new User(id, nom, prenom, username, passwd));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public boolean deleteUserByUsername(String username) {
		// Affichage de la confirmation avant la suppression
		boolean confirmation = showConfirmationAlert("Confirmation de suppression",
				"Êtes-vous sûr de vouloir supprimer cet utilisateur ?");

		if (confirmation) {
			String query = "DELETE FROM users WHERE username = ?";

			try (Connection connection = DBConnection.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(query)) {

				preparedStatement.setString(1, username);
				int rowsAffected = preparedStatement.executeUpdate();

				// Vérification si la suppression a réussi 
				if (rowsAffected > 0) {
					// Suppression réussie, pas d'alerte supplémentaire
					return true; // Suppression réussie
				} else {
					// Si aucun utilisateur n'est trouvé, affichage de l'alerte d'erreur
					showErrorAlert("Erreur de suppression", "Aucun utilisateur trouvé avec ce nom d'utilisateur.");
					return false; // Suppression échouée
				}
			} catch (SQLException e) {
				// Affichage d'une alerte d'erreur SQL si une exception se produit
				showErrorAlert("Erreur de base de données",
						"Une erreur est survenue lors de la suppression de l'utilisateur.");
				e.printStackTrace();
				return false;
			}
		} else {
			// Si l'utilisateur annule la suppression, pas d'alerte supplémentaire
			return false; // Suppression annulée, aucune alerte
		}
	}

	
	private boolean showConfirmationAlert(String title, String message) {
		// Création de l'alerte de confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		// Créer deux boutons : "OK" et "Annuler"
		ButtonType buttonTypeOK = new ButtonType("OK");
		ButtonType buttonTypeCancel = new ButtonType("Annuler");
		alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

		// Affichage de l'alerte et attente de la réponse de l'utilisateur
		Optional<ButtonType> result = alert.showAndWait();

		// Retourne true si l'utilisateur a cliqué sur "OK", sinon false
		return result.isPresent() && result.get() == buttonTypeOK;
	}

	
	private void showErrorAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
=======

public class UserService {

}
>>>>>>> 4d7ecc9cd46d8d203aa1d013f7ab9cff19dc8d3d
