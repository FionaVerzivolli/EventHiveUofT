package app.use_case.modify_event;

import app.entity.Event.Event;

/**
 * Data Access Object for the Modify Event Use Case.
 */
public interface ModifyEventUserDataAccessInterface {
    /**
     * Finds an event by its ID.
     * @param eventId The ID of the event to find.
     * @return The Event object if found, null otherwise.
     */
    Event findEventById(String eventId);

    /**
     * Updates the event with new values.
     * @param event The event to update.
     * @return true if the event was successfully updated, false otherwise.
     */
    boolean updateEvent(Event event);
}