package app.use_case.rsvp_event;

import app.entity.Event.Event;
import app.entity.User.User;

public class RSVPEventInteractor implements RSVPEventInputBoundary {
    private final RSVPEventUserDataAccessInterface rsvpEventDataAccessObject;
    private final RSVPEventOutputBoundary rsvpEventPresenter;

    public RSVPEventInteractor(RSVPEventUserDataAccessInterface rsvpEventDataAccessObject,
                               RSVPEventOutputBoundary rsvpEventPresenter) {
        this.rsvpEventDataAccessObject = rsvpEventDataAccessObject;
        this.rsvpEventPresenter = rsvpEventPresenter;
    }

    @Override
    public void execute(RSVPEventInputData rsvpEventInputData) {
        Event event = rsvpEventDataAccessObject.findEventById(rsvpEventInputData.getEventId());
        User user = rsvpEventDataAccessObject.findUserByUsername(rsvpEventInputData.getUsername());

        if (event == null) {
            rsvpEventPresenter.prepareFailView("Event not found.");
        } else if (user == null) {
            rsvpEventPresenter.prepareFailView("User not found.");
        } else if (event.getAttendeesIdList().size() >= event.getCapacity()) {
            rsvpEventPresenter.prepareFailView("Event is at full capacity.");
        } else {
            rsvpEventDataAccessObject.addUserToEvent(user, event);
            RSVPEventOutputData outputData = new RSVPEventOutputData(user.getUsername(), event.getTitle());
            rsvpEventPresenter.prepareSuccessView(outputData);
        }
    }
}