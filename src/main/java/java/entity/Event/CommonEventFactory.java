package main.java.java.entity.Event;

import java.util.List;

/**
 * Factory for creating CommonEvents
 */
public class CommonEventFactory implements EventFactory {
    public Event create(String eventId, String organizer, String title, String description, String dateTime,
                        int capacity, int latitude, int longitude, List<String> tags) {
        return new CommonEvent (eventId, organizer, title, description, dateTime,
        capacity, latitude, longitude, tags);
    }
}
