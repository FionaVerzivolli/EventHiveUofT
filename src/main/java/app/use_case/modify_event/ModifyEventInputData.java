package app.use_case.modify_event;

import java.util.List;

/**
 * Input Data for the Modify Event Use Case.
 */
public class ModifyEventInputData {
    private final String eventId;
    private final String oldTitle;
    private final String updatedTitle;
    private final String updatedDescription;
    private final String updatedDateTime;
    private final int updatedCapacity;
    private final float updatedLatitude;
    private final float updatedLongitude;
    private final boolean deleteEvent;
    public List<String> updatedTags;
    public String updatedOrganizer;

    /**
     * Constructor for ModifyEventInputData.
     * @param eventId The ID of the event to modify.
     * @param oldTitle the old title of the event
     * @param updatedTitle The updated title of the event.
     * @param updatedDescription The updated description of the event.
     * @param updatedDateTime The updated date and time of the event.
     * @param updatedCapacity The updated capacity of the event.
     * @param updatedLatitude The updated latitude of the event.
     * @param updatedLongitude The updated longitude of the event.
     */

    public ModifyEventInputData(String eventId, String oldTitle, String updatedTitle, String updatedDescription, String updatedDateTime,
                                int updatedCapacity, float updatedLatitude, float updatedLongitude, boolean deleteEvent,
                                List<String> updatedTags, String updatedOrganizer) {
        this.eventId = eventId;
        this.oldTitle = oldTitle;
        this.updatedTitle = updatedTitle;
        this.updatedDescription = updatedDescription;
        this.updatedDateTime = updatedDateTime;
        this.updatedCapacity = updatedCapacity;
        this.updatedLatitude = updatedLatitude;
        this.updatedLongitude = updatedLongitude;
        this.deleteEvent = deleteEvent;
        this.updatedTags = updatedTags;
        this.updatedOrganizer = updatedOrganizer;
    }
    public boolean getDeleteEvent () {
        return this.deleteEvent;
    }
    public String getOldTitle() {
        return oldTitle;
    }
    public String getEventId() {
        return eventId;
    }

    public String getUpdatedTitle() {
        return updatedTitle;
    }

    public String getUpdatedDescription() {
        return updatedDescription;
    }

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public int getUpdatedCapacity() {
        return updatedCapacity;
    }

    public float getUpdatedLatitude() {
        return updatedLatitude;
    }

    public float getUpdatedLongitude() {
        return updatedLongitude;
    }

    public List<String> getUpdatedTags() {
        return this.updatedTags;
    }

    public String getUpdatedOrganizer() {
        return this.updatedOrganizer;
    }
}