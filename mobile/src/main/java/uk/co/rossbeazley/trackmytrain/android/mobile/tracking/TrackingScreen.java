package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class TrackingScreen {
    private OnScreenTrackedServiceView serviceView;

    public TrackingScreen(FindsView findsView) {
        serviceView = new OnScreenTrackedServiceView(findsView);
        TrackMyTrainApp.instance.attach(serviceView);

    }

    public void invoke() {
        TrackMyTrainApp.instance.detach(serviceView);
        serviceView=null;
    }
}
