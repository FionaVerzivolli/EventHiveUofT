package app.interface_adapter.create_event;

import java.util.ArrayList;
import java.util.List;

public class CreateEventState {
    private String title, description, eventId, organizer;
    private String dateTimeError, capacityError, latitudeError, longitudeError, dateTime;
    private float longitude, latitude;
    private int capacity;
    private List<String> tags;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public String getDateTimeError() { return dateTimeError; }
    public void setDateTimeError(String dateTimeError) { this.dateTimeError = dateTimeError; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getOrganizer() { return organizer; }
    public void setOrganizer(String organizer) { this.organizer = organizer; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getCapacityError() { return capacityError; }
    public void setCapacityError(String capacityError) { this.capacityError = capacityError; }

    public float getLongitude() { return longitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }

    public String getLongitudeError() { return longitudeError; }
    public void setLongitudeError(String longitudeError) { this.longitudeError = longitudeError; }

    public float getLatitude() { return latitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }

    public String getLatitudeError() { return latitudeError; }
    public void setLatitudeError(String latitudeError) { this.latitudeError = latitudeError; }

    public List<String> getTags() { return tags; }
    public void setTags(String tags) {
        ArrayList<String> tagger = new ArrayList<>();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i<tags.length(); i++){
            if (tags.charAt(i) == ',') {
                tagger.add(ret.toString());
            } else {
                ret.append(tags.charAt(i));
            }
        }
        tagger.add(ret.toString());
        this.tags = tagger;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("SignupState{"
                + "title='" + title + '\''
                + ", description='" + description + '\''
                + ", dateTime='" + dateTime + '\''
                + ", eventId='" + eventId + '\''
                + ", organizer='" + organizer + '\''
                + ", capacity='" + capacity + '\''
                + ", latitude='" + latitude + '\''
                + ", longitude='" + longitude + '\''
                + ", tags='");

        for (String t : tags) {
            ret.append(t).append(' ');
        }
        ret.append('\'');
        ret.append('}');
        return ret.toString();
    }
}
