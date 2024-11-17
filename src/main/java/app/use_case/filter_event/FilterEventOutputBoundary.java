package app.use_case.filter_event;


public interface FilterEventOutputBoundary {
    /**
     * Prepares the success view for the Filter Event use case.
     * @param outputData The output data including the list of filtered events.
     */
    void prepareSuccessView(FilterEventOutputData outputData);

    /**
     * Prepares the failure view for the Filter Event use case.
     * @param errorMessage The explanation of the failure.
     */
    void prepareFailView(String errorMessage);
}