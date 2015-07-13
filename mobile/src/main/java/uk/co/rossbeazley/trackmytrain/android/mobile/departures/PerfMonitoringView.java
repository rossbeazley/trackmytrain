package uk.co.rossbeazley.trackmytrain.android.mobile.departures;


import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.Analytics;

public class PerfMonitoringView implements DeparturesView {

    private long timer;
    private Analytics tracker;

    public PerfMonitoringView(Analytics tracker) {
        this.tracker = tracker;
    }

    @Override
    public void present(DeparturesViewModel trains) {
        long millis = System.currentTimeMillis() - timer;
        final String category = "DeparturesQuery";
        final String variable = "DeparturesQuery.load";
        tracker.timing(millis, category, variable);
    }

    @Override
    public void loading() {
        timer = System.currentTimeMillis();
    }

    @Override
    public void error(TMTError error) {
        final String category = "DeparturesQuery";
        final String label = "DeparturesQuery.error";
        tracker.event(category, label);
    }

}
