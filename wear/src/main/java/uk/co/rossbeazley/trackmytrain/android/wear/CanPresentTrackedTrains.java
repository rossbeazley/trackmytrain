package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen.ServiceView;

public interface CanPresentTrackedTrains {

    void attach(ServiceView serviceView);

    void detach(ServiceView serviceView);
}
