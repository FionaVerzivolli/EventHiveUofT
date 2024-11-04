package java.eventure;

import java.util.ArrayList;
import java.util.List;

abstract class User {
    private String userID;
    private String displayName;
    private String contactInfo;
    private String bio;
    private boolean isVerified;
    private List<Event> rsvpEvents;
    private List<Event> pastEvents;

    public User(String userID, String displayName, String contactInfo, String bio, boolean isVerified) {
        this.userID = userID;
        this.displayName = displayName;
        this.contactInfo = contactInfo;
        this.bio = bio;
        this.isVerified = isVerified;
        this.rsvpEvents = new ArrayList<>();
        this.pastEvents = new ArrayList<>();
    }
}
