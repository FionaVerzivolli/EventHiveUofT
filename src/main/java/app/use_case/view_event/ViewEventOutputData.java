package app.use_case.view_event;

import app.entity.Event.Event;
import java.util.List;

public class ViewEventOutputData {
    private final List<Event> events;

    public ViewEventOutputData(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}