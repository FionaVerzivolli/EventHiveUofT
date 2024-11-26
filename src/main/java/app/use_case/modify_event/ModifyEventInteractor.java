package app.use_case.modify_event;

import app.entity.Event.Event;
import app.entity.Event.EventFactory;

import app.use_case.modify_event.ModifyEventUserDataAccessInterface;

/**
 * The ModifyEventInteractor handles the logic of modifying events (edit or delete).
 * Uses the Event Data Access Interface to update the event details.
 */
public class ModifyEventInteractor implements ModifyEventInputBoundary {
    private final ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject;
    private final ModifyEventOutputBoundary modifyEventPresenter;
    EventFactory eventFactory;

    public ModifyEventInteractor(ModifyEventUserDataAccessInterface modifyEventUserDataAccessObject,
                                 ModifyEventOutputBoundary modifyEventPresenter,
                                 EventFactory eventFactory   ) {
        this.modifyEventUserDataAccessObject = modifyEventUserDataAccessObject;
        this.modifyEventPresenter = modifyEventPresenter;
        this.eventFactory = eventFactory;
    }

    @Override
    public void execute(ModifyEventInputData modifyEventInputData) {
        String eventName = modifyEventInputData.getOldTitle();
        System.out.println("Rizzler was here");
        System.out.println(modifyEventInputData.getDeleteEvent());
        if (eventName == null) {
            modifyEventPresenter.prepareFailView("Event not found.");
        } else if (modifyEventInputData.getDeleteEvent()) {
            try {
                System.out.println("Delete event");
                modifyEventUserDataAccessObject.deleteEvent(eventName);
                ModifyEventOutputData outputData = new ModifyEventOutputData("Temp", eventName, "Event deleted successfully.");
                modifyEventPresenter.prepareSuccessView(outputData);
            } catch (Exception e) {
                modifyEventPresenter.prepareFailView("Failed to delete event. Reason: " + e.getMessage());
            }
        } else {
            try {
                Event event = eventFactory.create(
                        modifyEventInputData.getEventId(),
                        modifyEventInputData.getUpdatedOrganizer(),
                        modifyEventInputData.getUpdatedTitle(),
                        modifyEventInputData.getUpdatedDescription(),
                        modifyEventInputData.getUpdatedDateTime(),
                        modifyEventInputData.getUpdatedCapacity(),
                        modifyEventInputData.getUpdatedLatitude(),
                        modifyEventInputData.getUpdatedLongitude(),
                        modifyEventInputData.getUpdatedTags()
                );
                System.out.println("Big Justicesd    was here");
                modifyEventUserDataAccessObject.deleteEvent(eventName);
                modifyEventUserDataAccessObject.saveEvent(event);
                ModifyEventOutputData outputData = new ModifyEventOutputData("Temp", eventName, "Event deleted successfully.");
                modifyEventPresenter.prepareSuccessView(outputData);
            } catch (Exception e) {
                    modifyEventPresenter.prepareFailView("Failed to delete event. Reason: " + e.getMessage());
            }
        }
    }
}
