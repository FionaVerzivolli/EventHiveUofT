package java.eventure;

import java.util.List;

public class PartyEvent extends Event implements ProvidedFood, Category{
    private int ageLimit;

    public PartyEvent(String eventId, String title, String dateTime, String location, String description, int price, int capacity, List<String> tags, int ageLimit) {
        super(eventId, title, dateTime, location, description, price, capacity, tags);
        this.ageLimit = ageLimit;
    }
}
