package app.interface_adapter.modify_event;



import app.use_case.modify_event.ModifyEventInputBoundary;
import app.use_case.modify_event.ModifyEventInputData;

import java.util.List;

public class ModifyEventController {
    private final ModifyEventInputBoundary userEventUseCaseInteractor;

    public ModifyEventController(ModifyEventInputBoundary userEventUseCaseInteractor) {
        this.userEventUseCaseInteractor = userEventUseCaseInteractor;
    }

    /**
     * Execute the Event use Case
     * @param oldTitle
     * @param description
     * @param dateTime
     * @param capacity
     * @param latitude
     * @param longitude
     * @param tags
     * @param eventId
     * @param organizer
     */
    public void execute(String oldTitle, String updatedTitle, String updatedDescription, String updatedDateTime, int updatedCapacity, float updatedLatitude,
                        float updatedLongitude, boolean deleteEvent, List<String> updatedTags, String eventId, String updatedOrganizer) {
        final ModifyEventInputData modifyEventInputData = new ModifyEventInputData( eventId, oldTitle, updatedTitle,
                updatedDescription, updatedDateTime, updatedCapacity, updatedLatitude, updatedLongitude, deleteEvent, updatedTags, updatedOrganizer);
        this.userEventUseCaseInteractor.execute(modifyEventInputData);
    }
}
