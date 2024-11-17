package app.use_case.rsvp_event;


public interface RSVPEventOutputBoundary {

    void prepareSuccessView(RSVPEventOutputData outputData);
    void prepareFailView(String errorMessage);
}