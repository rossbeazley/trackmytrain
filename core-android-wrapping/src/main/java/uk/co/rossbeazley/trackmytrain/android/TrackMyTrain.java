package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trackedService.Tracking;

public class TrackMyTrain implements  CanTrackService, CanQueryDepartures {

    private final Tracking tracking;

    private final DepartureQueries departures;

    public TrackMyTrain(Tracking tracking, DepartureQueries departureQueries) {
        this.tracking = tracking;
        this.departures = departureQueries;
    }

//departures
    //core
    @Override
    public void departures(Station at, Direction direction, final DepartureQueryListener result) {
        departures.departures(at, direction, result);
    }

    public void addDepartureQueryListener(DepartureQueryListener departureQueryListener) {
        departures.addDepartureQueryListener(departureQueryListener);
    }

    @Override
    public DepartureQuery lastQuery() {
        return departures.lastQuery();
    }

//tracking
    //core
    @Override
    public void addTrackedServiceListener(TrackedServiceListener trackedServiceListener) {
        tracking.addTrackedServiceListener(trackedServiceListener);
    }

    @Override
    public void watchService(String serviceId) {
        tracking.watchService(serviceId);
    }

    @Override
    public boolean isTracking() {
        return tracking.isTracking();
    }

    @Override
    public void unwatchService() {
        tracking.unwatchService();
    }

}
