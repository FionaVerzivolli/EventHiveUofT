package java.eventure;

import java.util.List;

public class Location {
    private String address;
    private List<Event> events;


    public Location(String address, List<Event> events) {
        this.address = address;
        this.events = events;
    }
}
