package uk.co.rossbeazley.trackmytrain.android.mobile.departures;


import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;

public class PerfMonitoringView implements DeparturesView, CanQueryDepartures.DepartureQueryListener {

    private long timer;
    private Analytics tracker;

    public PerfMonitoringView(Analytics tracker, Clock clock) {
        this.tracker = tracker;
    }

    @Override
    public void present(DeparturesViewModel trains) {
        long millis = timeInMillis() - timer;
        final String category = "DeparturesQuery";
        final String variable = "DeparturesQuery.load";
        tracker.timing(millis, category, variable);
    }

    private long timeInMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public void loading() {
        timer = timeInMillis();
    }

    @Override
    public void success(List<Train> expectedList) {

    }

    @Override
    public void error(TMTError error) {
        final String category = "DeparturesQuery";
        final String label = "DeparturesQuery.error";
        tracker.event(new Analytics.EventTrack(category, label));
    }

}
