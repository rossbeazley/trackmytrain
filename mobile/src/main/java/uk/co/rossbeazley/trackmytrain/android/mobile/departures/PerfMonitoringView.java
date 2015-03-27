package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class PerfMonitoringView implements DeparturesView {

    private long timer;
    private Tracker tracker;

    public PerfMonitoringView(TrackMyTrainApp trackMyTrainApp) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(trackMyTrainApp);
        tracker = analytics.newTracker("UA-8505275-4");
    }

    @Override
    public void present(DeparturesViewModel trains) {
        long millis = System.currentTimeMillis() - timer;
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory("DeparturesQuery")
                .setValue(millis)
                .setVariable("DeparturesQuery.load")
                .build());
    }

    @Override
    public void loading() {
        timer = System.currentTimeMillis();
    }

    @Override
    public void error(String error) {

    }
}
