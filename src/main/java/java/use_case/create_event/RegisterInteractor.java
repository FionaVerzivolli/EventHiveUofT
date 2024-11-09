package main.java.java.use_case.create_event;


import main.java.java.entity.Event.Event;
import main.java.java.entity.Event.EventFactory;

/**
 * The Register Interactor
 * The Register Interactor handles the input
 * If a user with the inputted username exists, then get the userPresenter to prepare a fail view on frontend
 * Do a similar thing for other errors
 * Otherwise, interact with the database through hte userDAO to save the user and prepare a success view to the
 * front end.
 *
 * NOTE: userPresenter is just the outputBoundary class
 * Note: RegisterInteractor is an implementation of the inputBoundary interface
 */
public class RegisterInteractor implements RegisterInputBoundary {
    RegisterUserDataAccessInterface eventDataAccessObject;
    RegisterOutputBoundary eventPresenter;
    EventFactory eventFactory;

    public RegisterInteractor(RegisterUserDataAccessInterface eventDataAccessObject,
                              RegisterOutputBoundary eventPresenter,
                              EventFactory eventFactory) {

        this.eventDataAccessObject = eventDataAccessObject;
        this.eventPresenter = eventPresenter;
        this.eventFactory = eventFactory;
    }

    @Override
    public void execute(RegisterInputData registerInputData) {
        if (eventDataAccessObject.valid_coordinates(registerInputData.getLatitude(), registerInputData.getLongitude())) {
            eventPresenter.prepareFailView("Invalid coordinates");
        }
        else{
            final Event event = eventFactory.create(registerInputData.getEventId(), registerInputData.getOrganizer(),
                    registerInputData.getTitle(), registerInputData.getDescription(), registerInputData.getDateTime(),
                    registerInputData.getCapacity(), registerInputData.getLatitude(), registerInputData.getLongitude(),
                    registerInputData.getTags());
            eventDataAccessObject.save(event);

            final RegisterOutputData registerOutputData = new RegisterOutputData(event.getTitle(), false);
            eventPresenter.prepareSuccessView(registerOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        eventPresenter.switchToLoginView();
    }
}
