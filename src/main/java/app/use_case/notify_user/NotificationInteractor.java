package app.use_case.notify_user;

import app.entity.User.User;
import app.use_case.notify_user.NotificationUserDataAccessInterface;
import app.data_access.FirebaseService;


public class NotificationInteractor implements NotificationInputBoundary {
    private final NotificationUserDataAccessInterface notificationUserDataAccessObject;
    private final NotificationOutputBoundary notificationPresenter;
    private final FirebaseService firebaseService;

    public NotificationInteractor(NotificationUserDataAccessInterface notificationUserDataAccessObject,
                                  NotificationOutputBoundary notificationPresenter,
                                  FirebaseService firebaseService) {
        this.notificationUserDataAccessObject = notificationUserDataAccessObject;
        this.notificationPresenter = notificationPresenter;
        this.firebaseService = firebaseService;
    }

    @Override
    public void execute(NotificationInputData notificationInputData) {
        User user = notificationUserDataAccessObject.findUserByUsername(notificationInputData.getUsername());

        if (user == null) {
            notificationPresenter.prepareFailView("User not found.");
        } else {
            boolean emailSent = firebaseService.sendEmail(user.getEmail(), "Event Reminder", notificationInputData.getMessage());

            if (emailSent) {
                NotificationOutputData outputData = new NotificationOutputData(user.getUsername(), notificationInputData.getMessage());
                notificationPresenter.prepareSuccessView(outputData);
            } else {
                notificationPresenter.prepareFailView("Failed to send notification.");
            }
        }
    }
}