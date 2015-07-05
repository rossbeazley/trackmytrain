package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.content.Context;

import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public class ServiceTrackingNavigationController implements ServiceView {
    private final Context context;

    public ServiceTrackingNavigationController(Context context) {
        this.context = context;
    }

    @Override
    public void present(TrainViewModel train) {
        TrackingService.startTrackingService(context);
    }


    @Override
    public void hide() {
        TrackingService.stopTrackingService(context);
    }

    @Override
    public void trackingStarted() {

    }
}
