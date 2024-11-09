package main.java.java.use_case.create_event;


/**
 * Output data for the register user use case
 */
public class RegisterOutputData {

    private String title;

    private final boolean useCaseFailed;

    public RegisterOutputData(String title, boolean useCaseFailed) {
        this.title = title;
        this.useCaseFailed = useCaseFailed;
    }

    public String getTitle() {
        return title;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
