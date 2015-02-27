package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;

public class Departures extends Activity implements FindsView {

    private ListViewDeparturesView departureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        departureView = new ListViewDeparturesView(this);
        TrackMyTrainApp.instance.attach(departureView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TrackMyTrainApp.instance.detach(departureView);
        departureView=null;
    }
}
