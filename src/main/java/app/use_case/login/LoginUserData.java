package app.use_case.login;

import app.entity.User.User;

/**
 * Data Access Object for the Login Use Case.
 */
public interface LoginUserData {
    /**
     * Finds a user by the username.
     * @param username The username to search for.
     * @return The User object if found, otherwise null.
     */
    User findUserByUsername(String username);
}

