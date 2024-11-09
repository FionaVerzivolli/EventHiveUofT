package main.java.java.entity.Event;

import main.java.java.entity.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Event interface
 */
public class CommonEvent implements Event {
    private String eventId, organizer, title, description, dateTime;
    private List<main.java.java.entity.User.User> attendeesIdList;
    private int capacity, latitude, longitude;
    private List<String> tags;

    public CommonEvent(String eventId, String organizer, String title, String description, String dateTime,
                       int capacity, int latitude, int longitude, List<String> tags) {
        this.eventId = eventId;
        this.organizer = organizer;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
        this.attendeesIdList = new ArrayList<>();
    }

    /**
     * Returns the eventId
     * @return the eventId
     */
    @Override
    public String getEventId() {
        return eventId;
    }

    /**
     * Returns the event organizer
     * @return event organizer
     */
    @Override
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Returns the title of the event
     * @return the title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the event
     * @return the description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the date and time of the event
     * @return the data and time as a string
     */
    @Override
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Return the list of attendees in the list
     * @return the id of the attendees
     */
    @Override
    public List<User> getAttendeesIdList() {
        return attendeesIdList;
    }

    /**
     * Return the capacity of the event
     * @return the capacity
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Return the latitude of the event
     * @return the latitude
     */
    @Override
    public int getLatitude() {
        return latitude;
    }

    /**
     * Return the longitude of the event
     * @return the longitude
     */
    @Override
    public int getLongitude() {
        return longitude;
    }

    /**
     * Return the list of strings associated with the event
     * @return the list of tags
     */
    @Override
    public List<String> getTags() {
        return tags;
    }
}
