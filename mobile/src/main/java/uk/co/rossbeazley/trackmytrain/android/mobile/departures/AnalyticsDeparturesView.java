package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.Analytics;

public class AnalyticsDeparturesView implements DeparturesView {
    private final Analytics tracker;

    public AnalyticsDeparturesView(Analytics tracker) {
        this.tracker = tracker;
    }

    @Override
    public void present(DeparturesViewModel trains) {
        tracker.pageView("Departures Result");
    }

    @Override
    public void loading() {
        tracker.pageView("Departures Loading");
    }

    @Override
    public void error(TMTError error) {
        tracker.pageView("Departures Error");
    }
}
