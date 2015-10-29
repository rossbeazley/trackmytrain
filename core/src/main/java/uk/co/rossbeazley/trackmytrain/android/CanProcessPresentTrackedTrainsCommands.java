package uk.co.rossbeazley.trackmytrain.android;

public interface CanProcessPresentTrackedTrainsCommands {
    void watch(String serviceId);

    void unwatch();
}
