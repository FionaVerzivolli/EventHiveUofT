package app.use_case.rsvp_event;

import app.entity.Event.Event;
import app.entity.User.User;

public interface RSVPEventUserDataAccessInterface {
    /**
     * Finds an event by its ID.
     * @param eventId The ID of the event to find.
     * @return The Event object if found, null otherwise.
     */

    Event findEventById(String eventId);
    /**
     * Finds a user by their username.
     * @param username The username of the user to find.
     * @return The User object if found, null otherwise.
     */

    User findUserByUsername(String username);
    //Adds a user to an event's attendees list.

    void addUserToEvent(User user, Event event);
    // TODO: Plz check this method if competent after Integration of Google API.
}
