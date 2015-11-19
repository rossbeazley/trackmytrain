package fakes;

import uk.co.rossbeazley.trackmytrain.android.CanTrackService;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class CapturingTrackedServiceListener implements CanTrackService.TrackedServiceListener {
    public static final String UNKNOWN = "UNKNOWN";
    public static final String STARTED = "STARTED";
    public static final String STOPPED = "STOPPED";
    public Train train;
    public String tracking = UNKNOWN;

    @Override
    public void trackingStarted() {
        tracking = STARTED;
    }

    @Override
    public void trackedServiceUpdated(Train train) {

        this.train = train;
    }

    @Override
    public void trackingStopped() {
        tracking = STOPPED;
    }
}
