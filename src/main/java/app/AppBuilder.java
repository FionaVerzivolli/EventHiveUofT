package app;

import java.awt.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

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
import app.interface_adapter.login.LoginViewModel;
import app.interface_adapter.home.HomeViewModel;
import app.interface_adapter.modify_event.ModifyEventController;
import app.interface_adapter.modify_event.ModifyEventPresenter;
import app.interface_adapter.modify_event.ModifyEventViewModel;
import app.interface_adapter.register.RegisterController;
import app.interface_adapter.register.RegisterPresenter;
import app.interface_adapter.register.RegisterViewModel;
import app.use_case.create_event.EventInputBoundary;
import app.use_case.create_event.EventInteractor;
import app.use_case.display_event.DisplayEventInteractor;
import app.use_case.filter_event.FilterEventInputBoundary;
import app.use_case.filter_event.FilterEventInteractor;
import app.use_case.filter_event.FilterEventOutputBoundary;
import app.use_case.modify_event.ModifyEventInputBoundary;
import app.use_case.modify_event.ModifyEventInteractor;
import app.use_case.register.RegisterInputBoundary;
import app.use_case.register.RegisterInteractor;
import app.use_case.register.RegisterOutputBoundary;
import app.interface_adapter.create_event.CreateEventViewModel;

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
    private RegisterViewModel registerViewModel;

    private CreateEventView createEventView;

    private CreateEventViewModel createEventViewModel;
    private LoginViewModel loginViewModel;
    private CreateEventController createEventController;
    private FilterEventViewModel filterEventViewModel;
    private FilterEventView filterEventView;

    private ModifyEventView modifyEventView;
    private ModifyEventViewModel modifyEventViewModel;
    private ModifyEventController modifyEventController;

    // ensure that you are using card layout
    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }
    EventFactory eventFactory = new CommonEventFactory();
    // function to create and add the register view to the card layout
    public RegisterView addRegisterView() {
        // create new RegisterViewModel to manage state of the register view
        this.registerViewModel = new RegisterViewModel();
        this.registerView = new RegisterView(registerViewModel);

        // set the parent panel for navigation purposes (cardPanel contains all views in the card layout)
        registerView.setParentPanel(cardPanel);

        cardPanel.add(registerView, registerView.getViewName());
        return this.registerView;
    }

    public CreateEventView addCreateEventView() {
        this.createEventViewModel = new CreateEventViewModel();
        CreateEventPresenter eventPresenter = new CreateEventPresenter(null, createEventViewModel);
        EventInputBoundary createEventInputBoundary = new EventInteractor(
                eventDAO,
                eventPresenter,
                eventFactory
        );
        this.createEventController = new CreateEventController(createEventInputBoundary);
        this.createEventView = new CreateEventView(createEventViewModel, createEventController);
        createEventView.setParentPanel(cardPanel); // Set parentPanel
        cardPanel.add(createEventView, createEventView.getViewName());
        return createEventView;
    }

    public ModifyEventView addModifyEventView() {
        this.modifyEventViewModel = new ModifyEventViewModel("modifyEvent");
        ModifyEventPresenter modifyEventPresenter = new ModifyEventPresenter(null, modifyEventViewModel);
        EventFactory eventFactory = new CommonEventFactory();
        ModifyEventInputBoundary modifyEventInputBoundary = new ModifyEventInteractor(
                eventDAO,
                modifyEventPresenter,
                eventFactory
        );
        this.modifyEventController = new ModifyEventController(modifyEventInputBoundary);
        this.modifyEventView = new ModifyEventView(modifyEventViewModel, modifyEventController);
        modifyEventView.setParentPanel(cardPanel); // Set parentPanel
        cardPanel.add(modifyEventView,  modifyEventView.getViewName());
        return modifyEventView;
    }

    public HomeView addMainView() {
        HomeViewModel homeViewModel = new HomeViewModel();
        DisplayEventView displayEventView = new DisplayEventView();
        DisplayEventViewModel displayEventViewModel = new DisplayEventViewModel();
        DisplayEventPresenter DisplayEventPresenter = new DisplayEventPresenter(displayEventView, displayEventViewModel);

        DisplayEventInteractor displayEventInteractor = new DisplayEventInteractor(eventDAO, DisplayEventPresenter, eventFactory);
        DisplayEventController displayEventController = new DisplayEventController(displayEventInteractor);
        HomeView homeView = new HomeView(homeViewModel, displayEventController);
        homeView.setParentPanel(cardPanel);
        cardPanel.add(homeView, homeView.getViewName());
        return homeView;
    }

    public LoginView addLoginView() {
        if (this.loginViewModel == null) {
            this.loginViewModel = new LoginViewModel();
        }
        LoginView loginView = new LoginView(this.loginViewModel);
        loginView.setParentPanel(cardPanel);
        cardPanel.add(loginView, loginView.getViewName());

        return loginView;
    }

    public FilterEventView addFilterEventView() {
        this.filterEventViewModel = new FilterEventViewModel();
        this.filterEventView = new FilterEventView(filterEventViewModel);

        filterEventView.setParentPanel(cardPanel);

        cardPanel.add(filterEventView, filterEventView.getViewName());
        return this.filterEventView;
    }

    public AppBuilder addFilterEventUseCase() {
        if (filterEventView == null || filterEventView == null) {
            throw new IllegalStateException("RegisterView or RegisterViewModel is not initialized.");
        }
        final FilterEventOutputBoundary filterEventOutputBoundary = new FilterEventPresenter(viewManagerModel,
                filterEventViewModel);

        final FilterEventInputBoundary userFilterEventInteractor = new FilterEventInteractor(
                eventDAO, filterEventOutputBoundary);

        final FilterEventController controller = new FilterEventController(userFilterEventInteractor);

        filterEventView.setFilterEventsController(controller);

        return this;
    }


    public AppBuilder addRegisterUseCase() {
        // make sure RegisterView and RegisterViewModel are initialized (debugging)
        if (registerView == null || registerViewModel == null) {
            throw new IllegalStateException("RegisterView or RegisterViewModel is not initialized.");
        }
        // create input and output boundaries
        final RegisterOutputBoundary registerOutputBoundary = new RegisterPresenter(viewManagerModel,
                createEventViewModel, registerViewModel);
        final RegisterInputBoundary userRegisterInteractor = new RegisterInteractor(
                userDAO, registerOutputBoundary, userFactory);
        // create controller
        final RegisterController controller = new RegisterController(userRegisterInteractor);

        // set the RegisterController in the RegisterView
        registerView.setRegisterController(controller);

        return this;
    }


    public JFrame build() {
        // debugging
        System.out.println("Setting initial view to: " + registerView.getViewName());
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

//        // notify any listeners that the state of the ViewManagerModel has changed
//        viewManagerModel.firePropertyChanged();


        // return JFrame so it can be displayed in the application
        return application;

    }

    public JPanel getCardPanel() {
        return this.cardPanel;
    }
}
