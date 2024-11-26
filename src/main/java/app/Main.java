package app;

import app.view.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
@SpringBootApplication(scanBasePackages = {"app", "app.config"})
public class Main {

    /**
     * Builds and runs CA architecture of the application.
     *
     * @param args unused argument
     */
    public static void main(String[] args) {

        // check if running in headless mode
        if (java.awt.GraphicsEnvironment.isHeadless()) {
            System.out.println("Headless environment detected. GUI will not be initialized.");
            return;
        }

        // start the Spring Boot application context
        var context = SpringApplication.run(Main.class, args);

        // get AppBuilder bean from the Spring context
        AppBuilder appBuilder = context.getBean(AppBuilder.class);

        // add views to the application
        RegisterView registerView = appBuilder.addRegisterView();
        appBuilder.addRegisterUseCase();

        FilterEventView filterEventView = appBuilder.addFilterEventView();
        appBuilder.addFilterEventUseCase();


        LoginView loginView = appBuilder.addLoginView();
        HomeView homeView = appBuilder.addMainView();
        CreateEventView createEventView = appBuilder.addCreateEventView();
        ModifyEventView modifyEventView = appBuilder.addModifyEventView();

        // set parent panels for navigation purposes
        registerView.setParentPanel(appBuilder.getCardPanel());
        loginView.setParentPanel(appBuilder.getCardPanel());
        homeView.setParentPanel(appBuilder.getCardPanel());
        createEventView.setParentPanel(appBuilder.getCardPanel());
        modifyEventView.setParentPanel(appBuilder.getCardPanel());

        // build the application and display it
        final JFrame application = appBuilder.build();

        // account for headless error
        if (application != null) {
            application.pack();
            application.setVisible(true);
        } else {
            System.out.println("Application is running in headless mode. No GUI will be displayed.");
        }

        System.out.println("Application started successfully!");
    }
}
