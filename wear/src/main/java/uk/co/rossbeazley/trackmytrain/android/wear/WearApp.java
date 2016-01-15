package uk.co.rossbeazley.trackmytrain.android.wear;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.WearNotificationService;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearApp implements CanPresentTrackedTrains {
    private final HostNode hostNode;
    private final Postman postman;
    private final NotificationManager notificationManager;
    private List<ServiceView> serviceViews;
    private TrainViewModel currentService;

    public WearApp(HostNode hostNode, Postman postman, WearNotificationService service) {
        this.postman = postman;
        serviceViews = new CopyOnWriteArrayList<>();

        this.hostNode = hostNode;

        postman.broadcast(new AnalyticsEventMessage("WEAR-CREATED", "CREATED"));

        this.notificationManager = new NotificationManager(service);
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
        notificationManager.serviceTracking(trainViewModel);

    }

    void announceServiceTrackingStopped() {
        currentService = null;
        for (ServiceView serviceView : serviceViews) {
            serviceView.hide();
        }
        notificationManager.trackingStopped();
    }

    private void announceServiceTracking() {
        for (ServiceView serviceView : serviceViews) {
            serviceView.trackingStarted();
        }
        notificationManager.tracking();

    }

    @Override
    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
        announceServiceViewAttached(serviceView);
        notificationManager.serviceViewAttached();
    }

    private void announceServiceViewAttached(ServiceView serviceView) {
        if (currentService != null) serviceView.present(currentService);
        postman.broadcast(new AnalyticsEventMessage("WEAR-SERVICEVIEW-ATTACHED", serviceView.getClass().getSimpleName()));
    }

    @Override
    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
        notificationManager.serviceViewDetached(serviceViews.size());
    }

    public void attach(WearNotificationService.WearNotification notificationPresenter) {
        this.notificationManager.attach(notificationPresenter);
    }

    public void detach(WearNotificationService.WearNotification notificationPresenter) {
        notificationManager.detach(notificationPresenter);
    }

    private static class NotificationManager {

        private final WearNotificationService notificationService;
        private boolean isTracking;
        private boolean isNotifing;
        private List<WearNotificationService.WearNotification> notificationPresenters;

        public NotificationManager(WearNotificationService service) {

            this.notificationService = service;
            notificationPresenters = new CopyOnWriteArrayList<>();
        }

        public void serviceViewDetached(int size) {
            if (size == 0) {
                if (isTracking) {
                    isNotifing = true;
                    notificationService.show();
                }
            }
        }

        public void serviceViewAttached() {

            if (isNotifing) {
                notificationService.hide();
                isNotifing = false;
            }
        }

        public void tracking() {
            isTracking = true;
        }

        public void trackingStopped() {
            isTracking = false;
            if (isNotifing) {
                isNotifing = false;
                notificationService.hide();
            }
        }

        public void serviceTracking(TrainViewModel trainViewModel) {
            if (isNotifing) {
                for (WearNotificationService.WearNotification presenter : notificationPresenters) {
                    presenter.show(trainViewModel);
                }

            }
        }

        public void attach(WearNotificationService.WearNotification notificationPresenter) {
            this.notificationPresenters.add(notificationPresenter);
        }

        public void detach(WearNotificationService.WearNotification notificationPresenter) {
            notificationPresenters.remove(notificationPresenter);
        }
    }
}
