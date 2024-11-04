package java.eventure;
import java.util.List;

public class SportsEvent extends Event implements Category{
    private List<String> sports;
    private List<String> equipment;

    public SportsEvent(String eventId, String title, String dateTime, String location, String description, int price, int capacity, List<String> tags, List<String> equipmentList, List<String> sports){
        super(eventId, title, dateTime, location, description, price, capacity, tags);
        this.sports = sports;
        this.equipment = equipmentList;
    }
}
