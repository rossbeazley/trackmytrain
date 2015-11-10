package uk.co.rossbeazley.trackmytrain.android.wear;

public interface CanPresentTrackedTrains {

    void attach(ServiceView serviceView);

    void detach(ServiceView serviceView);
}
