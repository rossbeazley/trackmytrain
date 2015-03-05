package uk.co.rossbeazley.trackmytrain.android;

public interface TrackMyTrain {
    void departures(Station at, Direction direction);

    void watch(String serviceId);

    void unwatch();

    void attach(DeparturesView departureView);

    void detach(DeparturesView departuresView);

    void attach(ServiceView serviceView);

    void detach(ServiceView serviceView);


    void attach(DeparturesQueryView departuresQueryView);
}
