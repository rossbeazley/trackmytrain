package uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen;

import android.content.Context;

import uk.co.rossbeazley.trackmytrain.android.wear.notification.AndroidNotificationService;

//TODO write a connected test for this
public class StartsTrackingActivity implements ServiceViewNavigationController {

    private final Context context;

    public StartsTrackingActivity(Context context) {
        this.context = context;
    }

    @Override
    public void trackingStarted() {
        launchActivity();
        AndroidNotificationService.start(context);
    }


    void launchActivity() {
        TrackingActivity.launch(context);
    }
}
