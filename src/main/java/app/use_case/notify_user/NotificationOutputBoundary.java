package app.use_case.notify_user;

public interface NotificationOutputBoundary {

    void prepareSuccessView(NotificationOutputData outputData);
    void prepareFailView(String errorMessage);
}
