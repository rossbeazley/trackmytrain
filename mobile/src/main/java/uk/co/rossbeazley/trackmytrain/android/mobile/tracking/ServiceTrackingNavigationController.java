package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.content.Context;

import uk.co.rossbeazley.trackmytrain.android.CanTrackService;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public class ServiceTrackingNavigationController implements CanTrackService.TrackedServiceListener {
    private final Context context;

    public ServiceTrackingNavigationController(Context context) {
        this.context = context;
    }

    @Override
    public void trackingStarted() {
        TrackingService.startTrackingService(context);
    }

    @Override
    public void trackedServiceUpdated(Train train) {
        TrackingService.startTrackingService(context);
    }

    @Override
    public void trackingStopped() {
        TrackingService.stopTrackingService(context);
    }
}
