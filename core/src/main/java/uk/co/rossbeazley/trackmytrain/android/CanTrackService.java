package uk.co.rossbeazley.trackmytrain.android;

public interface CanTrackService {
    void addTrackedServiceListener(TrackedServiceListener trackedServiceListener);

    void watchService(String serviceId);

    boolean isTracking();

    void unwatchService();

    interface TrackedServiceListener {
        void trackingStarted();

        void trackedServiceUpdated(Train train);
    }
}
