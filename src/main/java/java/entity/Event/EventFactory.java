package main.java.java.entity.Event;

import java.util.List;

public interface EventFactory {
    /**
     * Creates a new Event
     * @param eventId the unique id of the event    //TODO: handle case so that eventId stays unique
     * @param organizer the name of the organizer
     * @param title the title of the event
     * @param description the description of the event
     * @param dateTime the date and time of the event   //TODO: Keep the format consistent "(HH:MM) (MM/DD/YYYY)"
     * @param capacity the capacity of the event
     * @param latitude the latitude of the event
     * @param longitude the longitude of the event
     * @param tags the tags of the event
     *
     * NOTE:
     * The parameters below are in event but they are just empty when we create a new event
     * private List<Event> rsvpEvents: A list of events the user has RSVPed to attend.
     *
     *
     */
    Event create(String eventId, String organizer, String title, String description, String dateTime,
                       int capacity, int latitude, int longitude, List<String> tags);
}
