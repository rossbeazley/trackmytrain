package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.os.Bundle;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.AndroidDeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.ListViewDeparturesView;

public class Departures extends Activity implements FindsView {

    private ListViewDeparturesView departureView;
    private OnScreenTrackedServiceView serviceView;
    private AndroidDeparturesQueryView departuresQueryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);

        departureView = new ListViewDeparturesView(this);
        serviceView = new OnScreenTrackedServiceView(this);
        departuresQueryView = new AndroidDeparturesQueryView(this);

        TrackMyTrainApp.instance.attach(departureView);
        TrackMyTrainApp.instance.attach(serviceView);
        TrackMyTrainApp.instance.attach(departuresQueryView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        TrackMyTrainApp.instance.detach(departureView);
        TrackMyTrainApp.instance.detach(serviceView);
        TrackMyTrainApp.instance.detach(departuresQueryView);

        departureView=null;
        serviceView=null;
        departuresQueryView=null;
    }

}
