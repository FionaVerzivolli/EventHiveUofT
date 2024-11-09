package main.java.java.use_case.create_event;

import java.util.List;

/**
 * Input Data for the Register Use Case
 * The View sends data to the controller which should have an actionListener, and then
 * will prepare data one triggered
 */
public class RegisterInputData {
    private String title;
    private String description;
    private String dateTime;
    private int capacity;
    private int latitude;
    private int longitude;
    private List<String> tags;
    private String eventId;
    private String organizer;

    /**
     * Constructor for RegisterInputData, the user registers an event by entering
     * the event title, description, dateTime, capacity, latitude, longitude, tags, eventId
     * and nothing else. The two other attributes createdEvents
     * and rsvpEvents are added to later
     * @param title
     * @param description
     * @param dateTime
     * @param capacity
     * @param latitude
     * @param longitude
     * @param tags
     * @param eventId
     * @param organizer
     */
    public RegisterInputData(String title, String description, String dateTime, int capacity, int latitude,
                             int longitude, List<String> tags, String eventId, String organizer) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
        this.eventId = eventId;
        this.organizer = organizer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getEventId() { return eventId; }

    public String getOrganizer() { return organizer; }
}
