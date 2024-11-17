package app.use_case.view_event;

import app.entity.Event.Event;
import java.util.List;

public class ViewEventInteractor implements ViewEventInputBoundary {
    private final ViewEventUserDataAccessInterface eventDataAccessObject;
    private final ViewEventOutputBoundary viewEventPresenter;

    public ViewEventInteractor(ViewEventUserDataAccessInterface eventDataAccessObject,
                               ViewEventOutputBoundary viewEventPresenter) {
        this.eventDataAccessObject = eventDataAccessObject;
        this.viewEventPresenter = viewEventPresenter;
    }

    @Override
    public void execute(ViewEventInputData viewEventInputData) {
        List<Event> events = eventDataAccessObject.findEvents(viewEventInputData);

        if (events.isEmpty()) {
            viewEventPresenter.prepareFailView("No events found with the specified criteria.");
        } else {
            ViewEventOutputData outputData = new ViewEventOutputData(events);
            viewEventPresenter.prepareSuccessView(outputData);
        }
    }
}