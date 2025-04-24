package helpers;

public class UserSession {

	private static UserSession instance;
	private int currentUserId;

	private UserSession() {
	}

	public static UserSession getInstance() {
		if (instance == null) {
			instance = new UserSession();
		}
		return instance;
	}

	public int getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(int id) {
		this.currentUserId = id;
	}

	public void clearSession() {
		currentUserId = 0;
	}

}
