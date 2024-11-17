package app.use_case.rsvp_event;


public class RSVPEventInputData {
    private final String username;
    private final String eventId;

    /**
     * Constructor for RSVPEventInputData, contains details for joining an event.
     * @param username The username of the user who wants to RSVP.
     * @param eventId The ID of the event that the user wants to RSVP to.
     */
    public RSVPEventInputData(String username, String eventId) {
        this.username = username;
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public String getEventId() {
        return eventId;
    }
}