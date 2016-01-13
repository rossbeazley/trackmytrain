package uk.co.rossbeazley.trackmytrain.android.wear;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearApp implements CanPresentTrackedTrains {
    private final HostNode hostNode;
    private final Postman postman;
    private final WearNotification notificationService;
    private List<ServiceView> serviceViews;
    private TrainViewModel currentService;

    public WearApp(HostNode hostNode, Postman postman, WearNotification service) {
        this.postman = postman;
        notificationService = service;
        serviceViews = new CopyOnWriteArrayList<>();

        this.hostNode = hostNode;

        postman.broadcast(new AnalyticsEventMessage("WEAR-CREATED", "CREATED"));
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
            announceServiceTracking(new TrainViewModel(msg.train()));
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
    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
        announcServiceViewAttached(serviceView);
    }

    private void announcServiceViewAttached(ServiceView serviceView) {
        if (currentService != null) serviceView.present(currentService);
        postman.broadcast(new AnalyticsEventMessage("WEAR-SERVICEVIEW-ATTACHED", serviceView.getClass().getSimpleName()));
    }

    @Override
    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
        if(serviceViews.size()==0) {
            notificationService.show(null);
        }
    }
}
