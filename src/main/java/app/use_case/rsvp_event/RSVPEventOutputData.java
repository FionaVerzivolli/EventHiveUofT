package app.use_case.rsvp_event;

public class RSVPEventOutputData {
    private final String username;
    private final String eventTitle;

    /**
     * Containing the details for a successful RSVP.
     * @param username The username of the user who RSVPed.
     * @param eventTitle The title of the event that the user RSVPed to.
     */
    public RSVPEventOutputData(String username, String eventTitle) {
        this.username = username;
        this.eventTitle = eventTitle;
    }

    public String getUsername() {
        return username;
    }

    public String getEventTitle() {
        return eventTitle;
    }
}