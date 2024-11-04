package java.eventure;

import java.util.List;

public class SocialEvent extends Event implements ProvidedFood, Category{
    private String dressCode;

    public SocialEvent(String eventId, String title, String dateTime, String location, String description, int price, int capacity, List<String> tags, String dressCode) {
        super(eventId, title, dateTime, location, description, price, capacity, tags);
        this.dressCode = dressCode;
    }

}
