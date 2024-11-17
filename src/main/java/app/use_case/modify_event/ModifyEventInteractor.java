package app.use_case.modify_event;

import app.entity.Event.Event;
import app.entity.Event.CommonEvent;
import app.use_case.modify_event.ModifyEventUserDataAccessInterface;

/**
 * The ModifyEventInteractor handles the logic of modifying events (edit or delete).
 * Uses the Event Data Access Interface to update the event details.
 */
public class ModifyEventInteractor implements ModifyEventInputBoundary {
    private final ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject;
    private final ModifyEventOutputBoundary modifyEventPresenter;

    public ModifyEventInteractor(ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject,
                                 ModifyEventOutputBoundary modifyEventPresenter) {
        this.modifyEventUserDataAccessObject = modifyEventUserDataAccessObject;
        this.modifyEventPresenter = modifyEventPresenter;
    }

    @Override
    public void execute(ModifyEventInputData modifyEventInputData) {
        Event event = modifyEventUserDataAccessObject.findEventById(modifyEventInputData.getEventId());

        if (event == null) {
            modifyEventPresenter.prepareFailView("Event not found.");
        } else {
            // Update the event with new values using setters
            event.setTitle(modifyEventInputData.getUpdatedTitle());
            event.setDescription(modifyEventInputData.getUpdatedDescription());
            event.setDateTime(modifyEventInputData.getUpdatedDateTime());
            event.setCapacity(modifyEventInputData.getUpdatedCapacity());
            event.setLatitude(modifyEventInputData.getUpdatedLatitude());
            event.setLongitude(modifyEventInputData.getUpdatedLongitude());

            boolean updateSuccessful = modifyEventUserDataAccessObject.updateEvent(event);

            if (updateSuccessful) {
                ModifyEventOutputData outputData = new ModifyEventOutputData(event.getEventId(), event.getTitle(), "Event updated successfully.");
                modifyEventPresenter.prepareSuccessView(outputData);
            } else {
                modifyEventPresenter.prepareFailView("Failed to update event.");
            }
        }
    }
}
