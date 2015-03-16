package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.DeparturesScreen;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.TrackingScreen;

public class Departures extends Activity implements FindsView {

    private DeparturesScreen departuresScreen;
    private TrackingScreen trackingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);

        departuresScreen = new DeparturesScreen(this,(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        //trackingScreen = new TrackingScreen(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //trackingScreen.invoke();
        departuresScreen.tearDown();
    }

}
