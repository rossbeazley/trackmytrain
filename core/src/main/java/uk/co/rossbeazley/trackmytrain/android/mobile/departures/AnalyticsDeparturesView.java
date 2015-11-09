package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import uk.co.rossbeazley.trackmytrain.android.CanTrackService;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;

public class AnalyticsDeparturesView implements CanTrackService.TrackedServiceListener {
    private final Analytics tracker;

    public AnalyticsDeparturesView(Analytics tracker) {
        this.tracker = tracker;
    }

    @Override
    public void trackingStarted() {
        tracker.pageView("Tracking_Loading");
    }

    @Override
    public void trackedServiceUpdated(Train train) {
        tracker.pageView("Tracking_Result");
    }

    @Override
    public void trackingStopped() {
        tracker.pageView("Tracking_Stopped");
    }
}
