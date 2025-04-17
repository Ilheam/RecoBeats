package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class User {

    // Attributs
    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty userName;
    private String password;
    private LocalDateTime createdAt;

    private List<String> preferences = new ArrayList<>();
    private List<String> playlist = new ArrayList<>();
    private List<String> favorites = new ArrayList<>();
    private List<String> history = new ArrayList<>();

    // Constructeur sans ID (utilisé à l'inscription)
    public User(String firstName, String lastName, String userName, String hashedPassword) {
        this.userId = new SimpleIntegerProperty(); // Initialisé à 0 par défaut
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.password = hashedPassword;
        this.createdAt = LocalDateTime.now();
    }

    // Constructeur avec ID
    public User(int id, String firstName, String lastName, String userName, String password) {
        this.userId = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.password = hashPassword(password);
        this.createdAt = LocalDateTime.now();
    }

    // Constructeur complet
    public User(int userId, String firstName, String lastName, String userName,
                String password, LocalDateTime createdAt,
                List<String> preferences, List<String> playlist,
                List<String> favorites, List<String> history) {
        this.userId = new SimpleIntegerProperty(userId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.userName = new SimpleStringProperty(userName);
        this.password = password; // Le mot de passe doit déjà être hashé
        this.createdAt = createdAt;
        this.preferences = preferences;
        this.playlist = playlist;
        this.favorites = favorites;
        this.history = history;
    }

    // Getters/Setters + JavaFX properties

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int value) {
        this.userId.set(value);
    }

    public SimpleIntegerProperty userIdProperty() {
        return this.userId;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String value) {
        this.firstName.set(value);
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String value) {
        this.lastName.set(value);
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String value) {
        this.userName.set(value);
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String rawPassword) {
        this.password = hashPassword(rawPassword);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    // Hashage du mot de passe avec BCrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Vérification du mot de passe
    public boolean checkPassword(String rawPassword) {
        return BCrypt.checkpw(rawPassword, this.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + getUserId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", userName='" + getUserName() + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
