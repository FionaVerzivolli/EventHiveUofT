package app;

import java.awt.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.text.View;

import app.data_access.UserDAO;
import app.data_access.EventDAO;
import app.entity.Event.CommonEventFactory;
import app.entity.Event.Event;
import app.entity.Event.EventFactory;
import app.entity.User.CommonUserFactory;
import app.entity.User.UserFactory;
import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.create_event.CreateEventController;
import app.interface_adapter.create_event.CreateEventPresenter;
import app.interface_adapter.display_event.DisplayEventController;
import app.interface_adapter.display_event.DisplayEventPresenter;
import app.interface_adapter.display_event.DisplayEventViewModel;
import app.interface_adapter.filter_event.FilterEventController;
import app.interface_adapter.filter_event.FilterEventPresenter;
import app.interface_adapter.filter_event.FilterEventViewModel;
import app.interface_adapter.home.HomeController;
import app.interface_adapter.home.HomePresenter;
import app.interface_adapter.login.LoginController;
import app.interface_adapter.login.LoginPresenter;
import app.interface_adapter.login.LoginViewModel;
import app.interface_adapter.home.HomeViewModel;
import app.interface_adapter.modify_event.ModifyEventController;
import app.interface_adapter.modify_event.ModifyEventPresenter;
import app.interface_adapter.modify_event.ModifyEventViewModel;
import app.interface_adapter.notify_users.NotifyUserController;
import app.interface_adapter.notify_users.NotifyUserPresenter;
import app.interface_adapter.register.RegisterController;
import app.interface_adapter.register.RegisterPresenter;
import app.interface_adapter.register.RegisterViewModel;
import app.interface_adapter.rsvp_event.RSVPController;
import app.interface_adapter.rsvp_event.RSVPPresenter;
import app.interface_adapter.rsvp_event.RSVPViewModel;
import app.interface_adapter.view_created_events.ViewCreatedEventsController;
import app.interface_adapter.view_created_events.ViewCreatedEventsPresenter;
import app.interface_adapter.view_created_events.ViewCreatedEventsViewModel;
import app.interface_adapter.view_event.ViewEventController;
import app.interface_adapter.view_event.ViewEventPresenter;
import app.interface_adapter.view_event.ViewEventViewModel;
import app.interface_adapter.view_rsvp.ViewRSVPController;
import app.interface_adapter.view_rsvp.ViewRSVPPresenter;
import app.interface_adapter.view_rsvp.ViewRSVPViewModel;
import app.use_case.create_event.EventInputBoundary;
import app.use_case.create_event.EventInteractor;
import app.use_case.display_event.DisplayEventInputBoundary;
import app.use_case.display_event.DisplayEventInteractor;
import app.use_case.create_event.EventOutputBoundary;
import app.use_case.display_event.DisplayEventOutputBoundary;
import app.use_case.display_event.DisplayEventOutputData;
import app.use_case.filter_event.FilterEventInputBoundary;
import app.use_case.filter_event.FilterEventInteractor;
import app.use_case.filter_event.FilterEventOutputBoundary;
import app.use_case.modify_event.ModifyEventInputBoundary;
import app.use_case.modify_event.ModifyEventInteractor;
import app.use_case.home.HomeInputBoundary;
import app.use_case.home.HomeInteractor;
import app.use_case.home.HomeOutputBoundary;
import app.use_case.login.LoginInputBoundary;
import app.use_case.login.LoginInteractor;
import app.use_case.login.LoginOutputBoundary;
import app.use_case.modify_event.ModifyEventOutputBoundary;
import app.use_case.notify_users.NotifyUserInputBoundary;
import app.use_case.notify_users.NotifyUserInteractor;
import app.use_case.notify_users.NotifyUserOutputBoundary;
import app.use_case.register.RegisterInputBoundary;
import app.use_case.register.RegisterInteractor;
import app.use_case.register.RegisterOutputBoundary;
import app.interface_adapter.create_event.CreateEventViewModel;

import app.use_case.rsvp_event.RSVPEventInputBoundary;
import app.use_case.rsvp_event.RSVPEventInteractor;
import app.use_case.rsvp_event.RSVPEventOutputBoundary;
import app.use_case.view_created_events.ViewCreatedInputBoundary;
import app.use_case.view_created_events.ViewCreatedInteractor;
import app.use_case.view_created_events.ViewCreatedOutputBoundary;
import app.use_case.view_event.ViewEventInputBoundary;
import app.use_case.view_event.ViewEventInteractor;
import app.use_case.view_event.ViewEventOutputBoundary;
import app.use_case.view_rsvp.ViewRSVPInputBoundary;
import app.use_case.view_rsvp.ViewRSVPInteractor;
import app.use_case.view_rsvp.ViewRSVPOutputBoundary;
import app.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *  * The AppBuilder class is responsible for putting together the pieces of
 *  * our CA architecture; piece by piece.
 *  * <p/>
 *  * This is done by adding each View and then adding related Use Cases.
 */@Component
public class AppBuilder {
    // initialize layouts
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EventDAO eventDAO;

    // initialize views and their models
    private RegisterView registerView;
    private CreateEventView createEventView;
    private LoginView loginView;
    private HomeView homeView;
    private FilterEventView filterEventView;
    private ViewEventView viewEventView;
    private ModifyEventView modifyEventView;
    private ViewRSVPView viewRSVPView;
    private ViewCreatedEventsView viewCreatedEventsView;


    private RegisterViewModel registerViewModel;
    private LoginViewModel loginViewModel;
    private CreateEventViewModel createEventViewModel;
    private FilterEventViewModel filterEventViewModel;
    private HomeViewModel homeViewModel;
    private DisplayEventViewModel displayEventViewModel;
    private ModifyEventViewModel modifyEventViewModel;
    private ViewEventViewModel viewEventViewModel;
    private ViewRSVPViewModel viewRSVPViewModel;
    private ViewCreatedEventsViewModel viewCreatedEventsViewModel;

    EventFactory eventFactory = new CommonEventFactory();
    // function to create and add the register view to the card layout


    // ensure that you are using card layout
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addRegisterView() {
        // create new RegisterViewModel to manage state of the register view
        this.registerViewModel = new RegisterViewModel();
        this.registerView = new RegisterView(registerViewModel);

        cardPanel.add(registerView, registerView.getViewName());
        return this;
    }


    public AppBuilder addCreateEventView() {
        this.createEventViewModel = new CreateEventViewModel();
        CreateEventPresenter eventPresenter = new CreateEventPresenter(viewManagerModel, createEventViewModel, homeViewModel);
        EventInputBoundary createEventInputBoundary = new EventInteractor(
                eventDAO,
                eventPresenter,
                eventFactory
        );
        CreateEventController createEventController = new CreateEventController(createEventInputBoundary);
//        CreateEventPresenter eventPresenter = new CreateEventPresenter(null, createEventViewModel);
//        EventFactory eventFactory = new CommonEventFactory();
//        EventInputBoundary createEventInputBoundary = new EventInteractor(
//                eventDAO,
//                eventPresenter,
//                eventFactory
//        );
//        this.createEventController = new CreateEventController(createEventInputBoundary);
        this.createEventView = new CreateEventView(createEventViewModel, createEventController);
        cardPanel.add(createEventView, createEventView.getViewName());
        return this;
    }

    public AppBuilder addModifyEventView() {
        modifyEventViewModel = new ModifyEventViewModel();
        modifyEventView = new ModifyEventView(modifyEventViewModel);

        cardPanel.add(modifyEventView, modifyEventViewModel.getViewName());
        return this;
    }

    public AppBuilder addMainView() {
        HomeViewModel homeViewModel = new HomeViewModel();
//        DisplayEventView displayEventView = new DisplayEventView();
        DisplayEventViewModel displayEventViewModel = new DisplayEventViewModel();
        DisplayEventPresenter DisplayEventPresenter = new DisplayEventPresenter(viewManagerModel, displayEventViewModel);

        DisplayEventInteractor displayEventInteractor = new DisplayEventInteractor(eventDAO, DisplayEventPresenter);
        DisplayEventController displayEventController = new DisplayEventController(displayEventInteractor);
        HomeView homeView = new HomeView(homeViewModel, displayEventController);
        homeView.setParentPanel(cardPanel);
        return this;
    }


    public AppBuilder addHomeView() {
        homeViewModel = new HomeViewModel();
        displayEventViewModel = new DisplayEventViewModel();
        final DisplayEventOutputBoundary displayEventOutputBoundary = new DisplayEventPresenter(viewManagerModel, displayEventViewModel);


        DisplayEventInputBoundary displayEventInteractor = new DisplayEventInteractor(eventDAO, displayEventOutputBoundary);
        ;
        DisplayEventController displayEventController = new DisplayEventController(displayEventInteractor);
        homeView = new HomeView(homeViewModel, displayEventController);
        cardPanel.add(homeView, homeView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, loginViewModel, registerViewModel, homeViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(userDAO, loginOutputBoundary);
        LoginController loginController = new LoginController(loginInteractor);
        loginView = new LoginView(loginViewModel, loginController);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addFilterEventView() {
        filterEventViewModel = new FilterEventViewModel();
        filterEventView = new FilterEventView(filterEventViewModel);

        cardPanel.add(filterEventView, filterEventView.getViewName());
        return this;
    }

    public AppBuilder addViewRSVPView() {
        viewRSVPViewModel = new ViewRSVPViewModel();
        viewRSVPView = new ViewRSVPView(viewRSVPViewModel);

        cardPanel.add(viewRSVPView, viewRSVPView.getViewName());
        return this;
    }

    public AppBuilder addViewCreatedEventsView() {
        viewCreatedEventsViewModel = new ViewCreatedEventsViewModel();
        viewCreatedEventsView = new ViewCreatedEventsView(viewCreatedEventsViewModel);

        cardPanel.add(viewCreatedEventsView, viewCreatedEventsView.getViewName());
        return this;
    }

    public AppBuilder addViewEventView() {
        viewEventViewModel = new ViewEventViewModel();
        viewEventView = new ViewEventView(viewEventViewModel);

        cardPanel.add(viewEventView, viewEventView.getViewName());
        return this;
    }

    public AppBuilder addFilterEventUseCase() {
        final FilterEventOutputBoundary filterEventOutputBoundary = new FilterEventPresenter(viewManagerModel,
                filterEventViewModel, homeViewModel);
        final ViewEventOutputBoundary viewEventOutputBoundary = new ViewEventPresenter(viewManagerModel, viewEventViewModel, homeViewModel);


        final FilterEventInputBoundary userFilterEventInteractor = new FilterEventInteractor(
                eventDAO, filterEventOutputBoundary);

        final ViewEventInputBoundary viewEventInteractor = new ViewEventInteractor(
                eventDAO, viewEventOutputBoundary
        );

        final FilterEventController filterEventController = new FilterEventController(userFilterEventInteractor);
        final ViewEventController viewEventController = new ViewEventController(viewEventInteractor);
        filterEventView.setFilterEventsController(filterEventController);
        filterEventView.setViewEventController(viewEventController);
        return this;
    }

    public AppBuilder addViewRSVPUseCase() {
        final ViewRSVPOutputBoundary viewRSVPOutputBoundary = new ViewRSVPPresenter(viewManagerModel,
                viewRSVPViewModel, homeViewModel);
        final ViewEventOutputBoundary viewEventOutputBoundary = new ViewEventPresenter(viewManagerModel, viewEventViewModel, homeViewModel);

        final ViewRSVPInputBoundary viewRSVPInteractor = new ViewRSVPInteractor(
                userDAO, viewRSVPOutputBoundary
        );

        final ViewEventInputBoundary viewEventInteractor = new ViewEventInteractor(
                eventDAO, viewEventOutputBoundary
        );

        final ViewRSVPController viewRSVPController = new ViewRSVPController(viewRSVPInteractor);
        viewRSVPView.setViewRSVPController(viewRSVPController);
        return this;
    }

    public AppBuilder addModifyEventUseCase() {
        final ModifyEventOutputBoundary modifyEventOutputBoundary = new ModifyEventPresenter(viewManagerModel,
                modifyEventViewModel, homeViewModel);

        final ModifyEventInputBoundary modifyEventInteractor = new ModifyEventInteractor(
                eventDAO, modifyEventOutputBoundary
        );

        final ModifyEventController modifyEventController = new ModifyEventController(modifyEventInteractor);
        modifyEventView.setModifyEventController(modifyEventController);
        return this;
    }

    public AppBuilder addRegisterUseCase() {
        // make sure RegisterView and RegisterViewModel are initialized (debugging)
        // create input and output boundaries
        System.out.println("Initializing Register Use Case...");
        // Ensure all required objects are initialized
        System.out.println("viewManagerModel: " + viewManagerModel);
        System.out.println("registerViewModel: " + registerViewModel);
        System.out.println("loginViewModel: " + loginViewModel);
        final RegisterOutputBoundary registerOutputBoundary = new RegisterPresenter(viewManagerModel, registerViewModel, loginViewModel);
        final RegisterInputBoundary userRegisterInteractor = new RegisterInteractor(
                userDAO, registerOutputBoundary, userFactory);
        // create controller
        final RegisterController controller = new RegisterController(userRegisterInteractor);

        // set the RegisterController in the RegisterView
        registerView.setRegisterController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new
                LoginPresenter(viewManagerModel,
                loginViewModel, registerViewModel, homeViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDAO, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addCreateEventUseCase() {
        final EventOutputBoundary eventOutputBoundary = new CreateEventPresenter(viewManagerModel, createEventViewModel, homeViewModel);

        final EventInputBoundary eventInputInteractor = new EventInteractor(eventDAO, eventOutputBoundary, eventFactory);

        final CreateEventController eventController = new CreateEventController(eventInputInteractor);
        createEventView.setCreateEventController(eventController);
        return this;
    }

    public AppBuilder addViewEventUseCase() {
        final ViewEventOutputBoundary viewEventOutputBoundary = new
                ViewEventPresenter(viewManagerModel,
                viewEventViewModel,
                homeViewModel);
        final RSVPEventOutputBoundary rsvpEventOutputBoundary = new RSVPPresenter(viewManagerModel, homeViewModel);

        final ViewEventInputBoundary viewEventInteractor = new ViewEventInteractor(
                eventDAO, viewEventOutputBoundary
        );
        final RSVPEventInputBoundary rsvpEventInteractor = new RSVPEventInteractor(eventDAO, rsvpEventOutputBoundary);
        final RSVPController  rsvpController = new RSVPController(rsvpEventInteractor);
        final ViewEventController viewEventController = new ViewEventController((viewEventInteractor));
        viewEventView.setRSVPEventController(rsvpController);
        viewEventView.setViewEventController(viewEventController);
        return this;
    }

    public AppBuilder addViewCreatedEventsUseCase() {
        final ViewCreatedOutputBoundary viewCreatedOutputBoundary = new ViewCreatedEventsPresenter(viewManagerModel,
                viewCreatedEventsViewModel, homeViewModel);

        final NotifyUserOutputBoundary notifyUserOutputBoundary = new NotifyUserPresenter(viewManagerModel, homeViewModel);


        final ViewCreatedInputBoundary viewCreatedInteractor = new ViewCreatedInteractor(
                eventDAO, viewCreatedOutputBoundary
        );

        final NotifyUserInputBoundary notifyUserInteractor = new NotifyUserInteractor(eventDAO, notifyUserOutputBoundary);

        final ViewCreatedEventsController viewCreatedEventsController = new ViewCreatedEventsController(viewCreatedInteractor);

        final NotifyUserController notifyUserController = new NotifyUserController(notifyUserInteractor);
        viewCreatedEventsView.setViewCreatedEventsController(viewCreatedEventsController);
        viewCreatedEventsView.setNotificationController(notifyUserController);

        return this;
    }

    public AppBuilder addHomeUseCase() {
        final HomeOutputBoundary homeOutputBoundary = new HomePresenter(viewManagerModel,
                loginViewModel,
                createEventViewModel, filterEventViewModel, viewRSVPViewModel, viewCreatedEventsViewModel, modifyEventViewModel);

        final ViewEventOutputBoundary viewEventOutputBoundary = new ViewEventPresenter(viewManagerModel, viewEventViewModel, homeViewModel);


        final HomeInputBoundary homeInteractor = new HomeInteractor(homeOutputBoundary);

        final ViewEventInputBoundary viewEventInteractor = new ViewEventInteractor(eventDAO, viewEventOutputBoundary);

        final HomeController homeController = new HomeController(homeInteractor);

        final ViewEventController viewEventController = new ViewEventController(viewEventInteractor);
        homeView.setHomeController(homeController);
        homeView.setViewEventController(viewEventController);
        return this;
    }


    public JFrame build() {
        // debugging
//        System.out.println("Setting initial view to: " + registerView.getViewName());
//        cardLayout.show(cardPanel, registerView.getViewName());
//        cardLayout.show(cardPanel, createEventView.getViewName());
//        cardLayout.show(cardPanel, filterEventView.getViewName());
//        cardLayout.show(cardPanel, modifyEventView.getViewName());

        // headless case
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Headless environment detected, skipping GUI initialization");
            return null;
        }

        // create main application window
        final JFrame application = new JFrame("EventHiveUofT");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // set the window to be maximized when the application starts
        application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // add the cardPanel to the JFrame, the main container with the CardLayout for switching views
        application.add(cardPanel);
        // set initial viewManagerModel to the register view
        viewManagerModel.setState(registerView.getViewName());
        // notify any listeners that the state of the ViewManagerModel has changed
        viewManagerModel.firePropertyChanged();
        // return JFrame so it can be displayed in the application
        return application;

    }
}
//    public JPanel getCardPanel() {
//        return this.cardPanel;
//    }