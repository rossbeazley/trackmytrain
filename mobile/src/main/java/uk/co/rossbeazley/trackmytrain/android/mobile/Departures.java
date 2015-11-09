package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import uk.co.rossbeazley.trackmytrain.android.CanProcessPresentTrackedTrainsCommands;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.DeparturesScreen;
public class Departures extends Activity implements FindsView {

    private DeparturesScreen departuresScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands = TrackMyTrainApp.canProcessPresentTrackedTrainsCommands;
        departuresScreen = new DeparturesScreen(this, inputMethodManager, canProcessPresentTrackedTrainsCommands);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        departuresScreen.tearDown();
    }

}
