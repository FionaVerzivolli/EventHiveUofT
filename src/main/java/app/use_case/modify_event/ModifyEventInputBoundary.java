package app.use_case.modify_event;


public interface ModifyEventInputBoundary {
    /**
     * Execute the event modification use case such as edit or delete an event
     * @param modifyEventInputData The input data containing details for modifying the event.
     */
    void execute(ModifyEventInputData modifyEventInputData);
}