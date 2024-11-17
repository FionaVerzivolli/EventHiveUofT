package app.use_case.filter_event;

import app.entity.Event.Event;
import java.util.List;


public class FilterEventOutputData {
    private final List<Event> filteredEvents;

    public FilterEventOutputData(List<Event> filteredEvents) {
        this.filteredEvents = filteredEvents;
    }

    public List<Event> getFilteredEvents() {
        return filteredEvents;
    }
}