package uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen;

import android.content.Context;

//TODO write a connected test for this
public class StartsTrackingActivity implements ServiceViewNavigationController {

    private final Context context;

    public StartsTrackingActivity(Context context) {
        this.context = context;
    }

    @Override
    public void trackingStarted() {
        TrackingActivity.launch(context);
    }


}
