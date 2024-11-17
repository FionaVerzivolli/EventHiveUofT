package app.use_case.filter_event;


public class FilterEventInputData {
    private final String category;
    private final String location;

    public FilterEventInputData(String category, String location) {
        this.category = category;   //The category of the event, e.g. "clubs", "parties", "sports"...etc.
        this.location = location;   //The location where the event is taking place
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }
}