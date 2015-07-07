package uk.co.rossbeazley.trackmytrain.android.wear;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.trackmytrain.android.CanTrackTrains;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearApp implements CanTrackTrains {
    private final HostNode hostNode;
    private List<ServiceView> serviceViews;

    public WearApp(HostNode hostNode) {
        serviceViews = new CopyOnWriteArrayList<>();

        this.hostNode = hostNode;
    }

    public void message(MessageEnvelope messageEnvelope) {
        hostNode.register(messageEnvelope.fromId());

        if (messageEnvelope.message() instanceof StartedTrackingMessage) {
            announceServiceTracking();
        }

        if (messageEnvelope.message() instanceof StoppedTrackingMessage) {
            announceServiceTrackingStopped();
        }
    }

    void announceServiceTrackingStopped() {
        for (ServiceView serviceView : serviceViews) {
            serviceView.hide();
        }
    }

    private void announceServiceTracking() {
        for (ServiceView serviceView : serviceViews) {
            serviceView.trackingStarted();
        }

    }

    @Override
    public void watch(String serviceId) {

    }

    @Override
    public void unwatch() {

    }

    @Override
    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
    }

    @Override
    public void detach(ServiceView serviceView) {

    }
}
