package app.use_case.view_event;

import app.entity.Event.Event;
import app.use_case.view_event.ViewEventInputData;
import java.util.List;


public interface ViewEventUserDataAccessInterface {
    /**
     * Finds events based on the provided filter criteria.
     * @param inputData The input data containing the filter criteria.
     * @return A list of Event objects that match the criteria.
     */
    List<Event> findEvents(ViewEventInputData inputData);
    // TODO: need to verify after API integration and method implementation in Service Layer
}
