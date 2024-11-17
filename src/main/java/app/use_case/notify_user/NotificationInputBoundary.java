package app.use_case.notify_user;


public interface NotificationInputBoundary {
    /**
     * Execute the notification use case to notify users of upcoming events.
     * @param notificationInputData The input data containing notification details.
     */
    void execute(NotificationInputData notificationInputData);
}