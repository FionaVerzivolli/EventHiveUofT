package java.eventure;
import java.util.ArrayList;
import java.util.List;

public class TrustedUser extends User{
    private List<Event> createdEvents;

    public TrustedUser(String userID, String displayName, String contactInfo, String bio, boolean isVerified){
        super(userID, displayName, contactInfo, bio, isVerified);
        this.createdEvents = new ArrayList<>();
    }


    public Event createEvent(String eventId, String title, String dateTime, String location, String description, int price, int capacity, List<String> tags, String additionalInfo) {
        Event newEvent = null;

        if (tags.contains("social")) {
            newEvent = new SocialEvent(eventId, title, dateTime, location, description, price, capacity, tags, additionalInfo);
        } else if (tags.contains("party")) {
            int ageLimit = Integer.parseInt(additionalInfo);
            newEvent = new PartyEvent(eventId, title, dateTime, location, description, price, capacity, tags, ageLimit);
        } else if (tags.contains("sports")) {
            String[] parts = additionalInfo.split(";");
            List<String> sportsList = List.of(parts[0].split(","));
            List<String> equipmentList = parts.length > 1 ? List.of(parts[1].split(",")) : List.of();

            newEvent = new SportsEvent(eventId, title, dateTime, location, description, price, capacity, tags, equipmentList, sportsList);
        }
        if (newEvent != null) {
            createdEvents.add(newEvent);
        }
        return newEvent;
    }

}
