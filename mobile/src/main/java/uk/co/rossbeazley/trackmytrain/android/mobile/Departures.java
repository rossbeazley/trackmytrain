package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.DeparturesScreen;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;

public class Departures extends Activity implements FindsView {

    private DeparturesScreen departuresScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        TrackedServicePresenter trackedServicePresenter = TrackMyTrainApp.trackedServicePresenter;
        DeparturesPresenter departuresPresenter = TrackMyTrainApp.departuresPresenter;
        departuresScreen = new DeparturesScreen(this, inputMethodManager, departuresPresenter, trackedServicePresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        departuresScreen.tearDown();
    }

}
