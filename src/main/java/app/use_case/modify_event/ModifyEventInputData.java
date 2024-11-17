package app.use_case.modify_event;

/**
 * Input Data for the Modify Event Use Case.
 */
public class ModifyEventInputData {
    private final String eventId;
    private final String updatedTitle;
    private final String updatedDescription;
    private final String updatedDateTime;
    private final int updatedCapacity;
    private final int updatedLatitude;
    private final int updatedLongitude;

    /**
     * Constructor for ModifyEventInputData.
     * @param eventId The ID of the event to modify.
     * @param updatedTitle The updated title of the event.
     * @param updatedDescription The updated description of the event.
     * @param updatedDateTime The updated date and time of the event.
     * @param updatedCapacity The updated capacity of the event.
     * @param updatedLatitude The updated latitude of the event.
     * @param updatedLongitude The updated longitude of the event.
     */

    public ModifyEventInputData(String eventId, String updatedTitle, String updatedDescription, String updatedDateTime,
                                int updatedCapacity, int updatedLatitude, int updatedLongitude) {
        this.eventId = eventId;
        this.updatedTitle = updatedTitle;
        this.updatedDescription = updatedDescription;
        this.updatedDateTime = updatedDateTime;
        this.updatedCapacity = updatedCapacity;
        this.updatedLatitude = updatedLatitude;
        this.updatedLongitude = updatedLongitude;
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

    public int getUpdatedLatitude() {
        return updatedLatitude;
    }

    public int getUpdatedLongitude() {
        return updatedLongitude;
    }
}