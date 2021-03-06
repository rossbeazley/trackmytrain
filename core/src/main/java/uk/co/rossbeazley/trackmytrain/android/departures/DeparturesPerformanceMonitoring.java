package uk.co.rossbeazley.trackmytrain.android.departures;


import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;
import uk.co.rossbeazley.trackmytrain.android.Clock;

public class DeparturesPerformanceMonitoring implements CanQueryDepartures.DepartureQueryListener {

    private long timer;
    private Analytics tracker;
    private Clock clock;

    public DeparturesPerformanceMonitoring(Analytics tracker, Clock clock) {
        this.tracker = tracker;
        this.clock = clock;
    }


    private long timeInMillis() {
        return clock.time();
    }

    @Override
    public void loading() {
        timer = timeInMillis();
    }

    @Override
    public void success(List<Train> expectedList) {
        long millis = timeInMillis() - timer;
        final String category = "DeparturesQuery";
        final String variable = "DeparturesQuery.load";
        tracker.timing(millis, category, variable);
    }

    @Override
    public void error(TMTError error) {
        final String category = "DeparturesQuery";
        final String label = "DeparturesQuery.error";
        long millis = timeInMillis() - timer;
        tracker.timing(millis, category, label);
        tracker.event(new Analytics.EventTrack(category, label));
    }

}
