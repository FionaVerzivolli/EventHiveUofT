package app.use_case.notify_user;

import app.entity.User.User;

public interface NotificationUserDataAccessInterface {
    /**
     * Finds a user by their username.
     * @param username The username of the user to find.
     * @return The User object if found, null otherwise.
     */
    User findUserByUsername(String username);
}