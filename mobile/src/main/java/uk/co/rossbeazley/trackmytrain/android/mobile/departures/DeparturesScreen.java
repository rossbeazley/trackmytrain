package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.inputmethod.InputMethodManager;

import uk.co.rossbeazley.trackmytrain.android.CanProcessPresentTrackedTrainsCommands;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class DeparturesScreen {

    private ListViewDeparturesView departureView;
    private AndroidDeparturesQueryView departuresQueryView;

    public DeparturesScreen(final FindsView findsView, InputMethodManager inputMethodManager, CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands) {
        departureView = new ListViewDeparturesView(findsView, canProcessPresentTrackedTrainsCommands);
        departuresQueryView = new AndroidDeparturesQueryView(findsView, inputMethodManager);
        TrackMyTrainApp.instance.attach(departureView);
        TrackMyTrainApp.instance.attach(departuresQueryView);
        TrackMyTrainApp.instance.attach(new NRELogoView(findsView));
        TrackMyTrainApp.trackedServicePresenter.attach(new TrackedServiceView(findsView, canProcessPresentTrackedTrainsCommands));
    }

    public void tearDown() {
        TrackMyTrainApp.instance.detach(departureView);
        TrackMyTrainApp.instance.detach(departuresQueryView);
        departureView=null;
        departuresQueryView=null;
    }

}
