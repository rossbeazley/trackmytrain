package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class DeparturesScreen {

    private ListViewDeparturesView departureView;
    private AndroidDeparturesQueryView departuresQueryView;

    public DeparturesScreen(FindsView findsView) {
        departureView = new ListViewDeparturesView(findsView);
        departuresQueryView = new AndroidDeparturesQueryView(findsView);
        TrackMyTrainApp.instance.attach(departureView);
        TrackMyTrainApp.instance.attach(departuresQueryView);
    }

    public void tearDown() {
        TrackMyTrainApp.instance.detach(departureView);
        TrackMyTrainApp.instance.detach(departuresQueryView);
        departureView=null;
        departuresQueryView=null;
    }
}
