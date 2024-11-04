package java.eventure;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Event> events;

    public Map() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }
}
