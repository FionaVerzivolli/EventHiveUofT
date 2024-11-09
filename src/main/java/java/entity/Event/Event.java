package main.java.java.entity.Event;

import main.java.java.entity.User.User;

import java.util.List;

/*
 * Representation of an event in the program
 */
public interface Event {

    /**
     * Returns the eventId
     * @return the eventId
     */
    public String getEventId();

    /**
     * Returns the event organizer
     * @return event organizer
     */
    public String getOrganizer();

    /**
     * Returns the title of the event
     * @return the title
     */
    public String getTitle();

    /**
     * Returns the description of the event
     * @return the description
     */
    public String getDescription();

    /**
     * Returns the date and time of the event
     * @return the data and time as a string
     */
    public String getDateTime();

    /**
     * Return the list of attendees in the list
     * @return the id of the attendees
     */
    public List<User> getAttendeesIdList();

    /**
     * Return the capacity of the event
     * @return the capacity
     */
    public int getCapacity();

    /**
     * Return the latitude of the event
     * @return the latitude
     */
    public int getLatitude();

    /**
     * Return the longitude of the event
     * @return the longitude
     */
    public int getLongitude();

    /**
     * Return the list of strings associated with the event
     * @return the list of tags
     */
    public List<String> getTags();
}
