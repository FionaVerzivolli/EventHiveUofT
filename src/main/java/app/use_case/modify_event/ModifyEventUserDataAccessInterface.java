package app.use_case.modify_event;

import app.entity.Event.Event;

/**
 * Data Access Object for the Modify Event Use Case.
 */
public interface ModifyEventUserDataAccessInterface {
    /**
     * Function to save an event to the Firebase Database.
     *
     * @param event, the event we want to save.
     */
    public void saveEvent(Event event);


    /**
     * Function to delete an event from the Firebase Database.
     *
     * @param eventName the name of the event to delete.
     */
    public void deleteEvent(String eventName);
}