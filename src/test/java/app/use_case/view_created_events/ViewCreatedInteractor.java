package app.use_case.view_created_events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewCreatedInteractorTest {

    private ViewCreatedDataAccessInterface viewCreatedDataAccessObject;
    private ViewCreatedOutputBoundary viewCreatedPresenter;
    private ViewCreatedInteractor interactor;

    private List<String> fetchedCreatedEvents;
    private String usernamePassedToDAO;
    private boolean switchToHomeViewCalled;

    @BeforeEach
    void setUp() {
        fetchedCreatedEvents = null;
        usernamePassedToDAO = null;
        switchToHomeViewCalled = false;

        viewCreatedDataAccessObject = username -> {
            usernamePassedToDAO = username;
            if ("validUser".equals(username)) {
                return Arrays.asList("Event1", "Event2", "Event3");
            } else if ("emptyUser".equals(username)) {
                return Collections.emptyList();
            }
            throw new IllegalArgumentException("Invalid username");
        };

        viewCreatedPresenter = new ViewCreatedOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewCreatedOutputData outputData) {
                fetchedCreatedEvents = outputData.getCreatedEvents();
            }

            @Override
            public void switchToHomeView() {
                switchToHomeViewCalled = true;
            }
        };

        interactor = new ViewCreatedInteractor(viewCreatedDataAccessObject, viewCreatedPresenter);
    }

    @Test
    void testExecute_Success() {
        ViewCreatedInputData inputData = new ViewCreatedInputData("validUser");

        interactor.execute(inputData);

        assertNotNull(fetchedCreatedEvents, "Fetched created events should not be null.");
        assertEquals(Arrays.asList("Event1", "Event2", "Event3"), fetchedCreatedEvents, "Fetched events do not match expected list.");
        assertEquals("validUser", usernamePassedToDAO, "Username passed to DAO is incorrect.");
        assertFalse(switchToHomeViewCalled, "Switch to Home View should not have been called.");
    }

    @Test
    void testExecute_EmptyEvents() {
        ViewCreatedInputData inputData = new ViewCreatedInputData("emptyUser");

        interactor.execute(inputData);

        assertNotNull(fetchedCreatedEvents, "Fetched created events should not be null.");
        assertTrue(fetchedCreatedEvents.isEmpty(), "Fetched events should be empty for user with no created events.");
        assertEquals("emptyUser", usernamePassedToDAO, "Username passed to DAO is incorrect.");
        assertFalse(switchToHomeViewCalled, "Switch to Home View should not have been called.");
    }

    @Test
    void testExecute_InvalidUsername() {
        ViewCreatedInputData inputData = new ViewCreatedInputData("invalidUser");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Invalid username", exception.getMessage(), "Exception message is incorrect.");
        assertNull(fetchedCreatedEvents, "Fetched created events should be null when an exception occurs.");
        assertEquals("invalidUser", usernamePassedToDAO, "Username passed to DAO is incorrect.");
        assertFalse(switchToHomeViewCalled, "Switch to Home View should not have been called.");
    }

    @Test
    void testSwitchToHomeView() {
        interactor.switchToHomeView();

        assertTrue(switchToHomeViewCalled, "Switch to Home View should have been called.");
    }
}