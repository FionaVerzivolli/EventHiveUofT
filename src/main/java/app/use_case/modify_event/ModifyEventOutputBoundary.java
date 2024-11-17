package app.use_case.modify_event;

public interface ModifyEventOutputBoundary {

    void prepareSuccessView(ModifyEventOutputData outputData);
    void prepareFailView(String errorMessage);
}