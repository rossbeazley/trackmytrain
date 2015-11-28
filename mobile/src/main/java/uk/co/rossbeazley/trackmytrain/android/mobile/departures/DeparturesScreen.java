package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.inputmethod.InputMethodManager;

import uk.co.rossbeazley.trackmytrain.android.CanPresentDepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;

public class DeparturesScreen {

    private final NRELogoView nreLogoView;
    private final TrackedServiceView trackedServiceView;
    private ListViewDeparturesView departureView;
    private AndroidDeparturesQueryView departuresQueryView;
    private CanPresentDepartureQueries departuresPresenter;
    private TrackedServicePresenter trackedServicePresenter;

    public DeparturesScreen(final FindsView findsView, InputMethodManager inputMethodManager, CanPresentDepartureQueries departuresPresenter, TrackedServicePresenter trackedServicePresenter) {
        this.departuresPresenter = departuresPresenter;
        this.trackedServicePresenter = trackedServicePresenter;
        departureView = new ListViewDeparturesView(findsView, trackedServicePresenter);
        departuresQueryView = new AndroidDeparturesQueryView(findsView, inputMethodManager);
        nreLogoView = new NRELogoView(findsView, departuresPresenter);

        departuresPresenter.attach(departureView);
        departuresPresenter.attach(departuresQueryView);
        departuresPresenter.attach(nreLogoView);

        trackedServiceView = new TrackedServiceView(findsView, trackedServicePresenter);
        trackedServicePresenter.attach(trackedServiceView);
    }

    public void tearDown() {
        departuresPresenter.detach(departureView);
        departuresPresenter.detach(departuresQueryView);
        departuresPresenter.detach(nreLogoView);

        trackedServicePresenter.detach(trackedServiceView);

        departureView=null;
        departuresQueryView=null;
    }

}
