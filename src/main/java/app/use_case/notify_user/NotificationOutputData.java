package app.use_case.notify_user;

public class NotificationOutputData {
    private final String username;
    private final String message;

    /**
     * Constructor for NotificationOutputData, contains details for a successful notification.
     * @param username The username of the user notified.
     * @param message The notification message sent to the user.
     */
    public NotificationOutputData(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}