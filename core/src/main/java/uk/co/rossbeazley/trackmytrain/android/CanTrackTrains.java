package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

public interface CanTrackTrains {

    void attach(ServiceView serviceView);

    void detach(ServiceView serviceView);
}
