package uk.co.rossbeazley.trackmytrain.android.wear;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.trackmytrain.android.CanTrackTrains;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearApp implements CanTrackTrains {
    private final HostNode hostNode;
    private List<ServiceView> serviceViews;
    private TrainViewModel currentService;

    public WearApp(HostNode hostNode, Postman postman) {
        serviceViews = new CopyOnWriteArrayList<>();

        this.hostNode = hostNode;

        postman.broadcast(new AnalyticsEventMessage("CREATED", ""));
    }

    public void message(MessageEnvelope messageEnvelope) {
        hostNode.register(messageEnvelope.fromId());

        final Postman.Message message = messageEnvelope.message();

        if (message instanceof StartedTrackingMessage) {
            announceServiceTracking();
        }

        if (message instanceof StoppedTrackingMessage) {
            announceServiceTrackingStopped();
        }

        if (message instanceof TrackedServiceMessage) {
            TrackedServiceMessage msg = (TrackedServiceMessage) message;
            announceServiceTracking(msg.trainViewModel());
        }
    }

    private void announceServiceTracking(TrainViewModel trainViewModel) {
        currentService = trainViewModel;
        for (ServiceView serviceView : serviceViews) {
            serviceView.present(trainViewModel);
        }

    }

    void announceServiceTrackingStopped() {
        currentService = null;
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
        if (currentService != null) serviceView.present(currentService);
    }

    @Override
    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
    }
}
