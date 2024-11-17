package app.use_case.login;

public interface LoginOutputBoundary {
    /**
     * Prepares the success view for the Login use case.
     * @param outputData The output data including relevant information after successful login.
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Prepares the failure view for the Login use case.
     * @param errorMessage The explanation of the failure.
     */
    void prepareFailView(String errorMessage);
}