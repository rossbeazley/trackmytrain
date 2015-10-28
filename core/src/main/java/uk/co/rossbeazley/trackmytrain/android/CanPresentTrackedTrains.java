package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

public interface CanPresentTrackedTrains {

    //ui
    void departures(Station at, Direction direction);

    void departures(DepartureQuery query);

    //tracking
        //ui
    void watch(String serviceId);

    void unwatch();

    void attach(ServiceView serviceView);

    void detach(ServiceView serviceView);
}
