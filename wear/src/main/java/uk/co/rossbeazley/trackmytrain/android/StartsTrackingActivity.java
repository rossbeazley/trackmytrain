package uk.co.rossbeazley.trackmytrain.android;

import android.content.Context;

import uk.co.rossbeazley.trackmytrain.android.wear.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.TrackingActivity;

//TODO write a connected test for this
class StartsTrackingActivity implements ServiceView {

    private final Context context;

    public StartsTrackingActivity(Context context) {
        this.context = context;
    }


    @Override
    public void present(uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel train) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void trackingStarted() {
        launchActivity();
    }


    void launchActivity() {
        TrackingActivity.launch(context);
    }
}
