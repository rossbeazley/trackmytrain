package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class DeparturesScreen {

    private ListViewDeparturesView departureView;
    private AndroidDeparturesQueryView departuresQueryView;

    public DeparturesScreen(final FindsView findsView, InputMethodManager inputMethodManager) {
        departureView = new ListViewDeparturesView(findsView);
        departuresQueryView = new AndroidDeparturesQueryView(findsView, inputMethodManager);
        TrackMyTrainApp.instance.attach(departureView);
        TrackMyTrainApp.instance.attach(departuresQueryView);
        TrackMyTrainApp.instance.attach(new DeparturesView() {

            private View nreLogo = findsView.findViewById(R.id.powered_by_nre);

            @Override
            public void present(DeparturesViewModel trains) {

            }

            @Override
            public void loading() {
                ((ViewGroup) (findsView.findViewById(R.id.servicedetails))).removeView(nreLogo);
                TrackMyTrainApp.instance.detach(this);
            }

            @Override
            public void error(TMTError error) {

            }

        });
    }

    public void tearDown() {
        TrackMyTrainApp.instance.detach(departureView);
        TrackMyTrainApp.instance.detach(departuresQueryView);
        departureView=null;
        departuresQueryView=null;
    }
}
