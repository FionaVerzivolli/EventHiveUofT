package app.use_case.filter_event;

import app.entity.Event.Event;
import java.util.List;

public interface FilterEventUserDataAccessInterface {
    /**
     * Finds events based on the provided filter criteria.
     * @param inputData The input data containing the filter criteria.
     * @return A list of Event objects that match the criteria.
     */
    List<Event> findEvents(FilterEventInputData inputData);
    //TODO: Verify method implementation after integration with Google Maps API.
    // Also make sure the method interacts with new Google Maps geolocation data and provides accurate event filtering
}