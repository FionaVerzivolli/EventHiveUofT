package app.use_case.filter_event;

public interface FilterEventInputBoundary {
    // @param filterEventInputData containing event filter criteria.

    void execute(FilterEventInputData filterEventInputData);
}