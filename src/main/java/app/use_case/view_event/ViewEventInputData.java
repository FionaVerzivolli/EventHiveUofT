package app.use_case.view_event;

public class ViewEventInputData {
    private final String category;
    private final String location;
    private final String organizer;

    /**
     * Constructor for ViewEventInputData, contains optional filters for viewing events.
     * @param category The category of the event, e.g. "clubs", "workshops".
     * @param location The location where the event is taking place.
     * @param organizer The organizer of the event.
     */
    public ViewEventInputData(String category, String location, String organizer) {
        this.category = category;
        this.location = location;
        this.organizer = organizer;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganizer() {
        return organizer;
    }
}
