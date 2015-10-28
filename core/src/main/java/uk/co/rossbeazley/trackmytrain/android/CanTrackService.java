package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.trackedService.Tracking;

public interface CanTrackService {
    void addTrackedServiceListener(Tracking.TrackedServiceListener trackedServiceListener);

    void watchService(String serviceId);

    boolean isTracking();

    void unwatchService();
}
