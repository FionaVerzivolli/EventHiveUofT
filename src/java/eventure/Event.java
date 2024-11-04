package java.eventure;
import java.util.ArrayList;
import java.util.List;

abstract class Event {
    private String eventId;
    private List<User> organizers;
    private List<User> contact_information;
    private String title;
    private String signUp;
    private String description;
    private String dateTime;
    private List<User> attendeesIdList;
    private int price;
    private int capacity;
    private List<String> tags;

    public Event(String eventId, String title, String dateTime, String location, String description, int price, int capacity, List<String> tags) {
        this.eventId = eventId;
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.tags = tags;
        this.attendeesIdList = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.contact_information = new ArrayList<>();
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);  //check if an event has a specific tag
    }
}
