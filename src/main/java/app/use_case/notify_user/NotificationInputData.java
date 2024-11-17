package app.use_case.notify_user;

public class NotificationInputData {
    private final String username;
    private final String eventId;
    private final String message;
    /**
     * Message parameter should contain details about the event, such as name,
     * time, and location, to provide the user with useful information
     */


    /**
     * Constructor for NotificationInputData.
     * @param username The username of the user to notify.
     * @param eventId The ID of the event the notification is related to.
     * @param message The notification message to be sent.
     */
    public NotificationInputData(String username, String eventId, String message) {
        this.username = username;
        this.eventId = eventId;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getEventId() {
        return eventId;
    }

    public String getMessage() {
        return message;
    }
}