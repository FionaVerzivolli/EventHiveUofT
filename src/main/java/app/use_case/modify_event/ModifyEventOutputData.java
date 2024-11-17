package app.use_case.modify_event;

public class ModifyEventOutputData {
    private final String eventId;
    private final String updatedTitle;
    private final String message;

    /**
     * Contains details for a modification.
     * @param eventId The ID of the event modified.
     * @param updatedTitle The updated title of the event.
     * @param message The success message.
     */
    public ModifyEventOutputData(String eventId, String updatedTitle, String message) {
        this.eventId = eventId;
        this.updatedTitle = updatedTitle;
        this.message = message;
    }

    public String getEventId() {
        return eventId;
    }

    public String getUpdatedTitle() {
        return updatedTitle;
    }

    public String getMessage() {
        return message;
    }
}