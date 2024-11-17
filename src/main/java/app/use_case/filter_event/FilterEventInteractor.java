package app.use_case.filter_event;

import app.entity.Event.Event;
import java.util.List;

// The FilterEventInteractor filtering events in the application, ituses the Event Data Access to
// retrieve relevant events and sends the results through the presenter.

public class FilterEventInteractor implements FilterEventInputBoundary {
    private final FilterEventUserDataAccessInterface eventDataAccessObject;
    private final FilterEventOutputBoundary filterEventPresenter;

    public FilterEventInteractor(FilterEventUserDataAccessInterface eventDataAccessObject,
                                 FilterEventOutputBoundary filterEventPresenter) {
        this.eventDataAccessObject = eventDataAccessObject;
        this.filterEventPresenter = filterEventPresenter;
    }

    @Override
    public void execute(FilterEventInputData filterEventInputData) {
        // Retrieve events based on the provided criteria.
        List<Event> filteredEvents = eventDataAccessObject.findEvents(filterEventInputData);

        if (filteredEvents.isEmpty()) {
            filterEventPresenter.prepareFailView("No events found matching the criteria.");
        } else {
            FilterEventOutputData outputData = new FilterEventOutputData(filteredEvents);
            filterEventPresenter.prepareSuccessView(outputData);
        }
    }
}