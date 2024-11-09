package main.java.java.use_case.create_event;


import main.java.java.entity.Event.Event;

/**
 * Data Access Object for the Register Use Case.
 * TODO: notice that although this file is in Use Case, it is implemented in the data_access
 * folder
 */
public interface RegisterUserDataAccessInterface {
    /**
     * Saves the event to the database
     * @param event to save onto the database
     */
    void save(Event event);

    /**
     * Checks if the coordinates are valid, True if it is
     * @param latitude the latitude coordinate
     * @param longitude the longitude coordinate
     * @return True if the coordinates are valid
     */
    boolean valid_coordinates(int latitude, int longitude);
}
