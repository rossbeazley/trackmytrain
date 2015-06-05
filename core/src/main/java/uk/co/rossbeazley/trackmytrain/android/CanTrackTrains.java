package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

/**
 * Created by beazlr02 on 02/06/2015.
 */
public interface CanTrackTrains {
    void watch(String serviceId);

    void unwatch();

    void attach(ServiceView serviceView);

    void detach(ServiceView serviceView);
}
