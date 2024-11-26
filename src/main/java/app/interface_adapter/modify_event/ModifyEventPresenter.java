package app.interface_adapter.modify_event;


import app.use_case.modify_event.ModifyEventOutputBoundary;
import app.use_case.modify_event.ModifyEventOutputData;
import app.view.ModifyEventView;

public class ModifyEventPresenter implements ModifyEventOutputBoundary {
    private final ModifyEventViewModel modifyEventViewModel;
    private final ModifyEventView modifyEventManagerModel;
    public ModifyEventPresenter(ModifyEventView modifyEventManagerModel,
                            ModifyEventViewModel modifyEventViewModel) {
        this.modifyEventManagerModel = modifyEventManagerModel;
        this.modifyEventViewModel = modifyEventViewModel;
    }

    /**
     * Prepares the success view for the Event user use case
     * This will probably just lead to the main screen of the application
     * whatever that ends up being
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(ModifyEventOutputData outputData) {

    }

    /**
     * Prepares the failure view for the Event user use case.
     * In this case, the result will probably be that you stay in the Event
     * view but it tells you there is an error with what you have done
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final ModifyEventState modifyEventState = modifyEventViewModel.getState();
        modifyEventState.setCapacityError(errorMessage); // Not sure which error I should use
        modifyEventViewModel.firePropertyChanged();
    }
}
