package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.wear.WearApp;

public class NotificationPresenter implements WearNotificationService.WearNotification {

    public static final String STOP_TRACKING_ACTION = "STOP_TRACKING_ACTION";


    private final WearNotificationService.NotificationView androidNotificationService;
    private final WearApp wearApp;

    public NotificationPresenter(WearNotificationService.NotificationView androidNotificationService, WearApp instance) {

        this.androidNotificationService = androidNotificationService;
        wearApp = instance;
        androidNotificationService.notify("Loading", "service data");
        instance.attach(this);
    }

    @Override
    public void show(TrainViewModel train) {

        String contentTitle = train.platform();
        String contentText = train.scheduledTime() + "\nexp " + train.estimatedTime();

        androidNotificationService.notify(contentTitle, contentText);
    }


    public void serviceAction(String action) {
        if (STOP_TRACKING_ACTION.equals(action)) {
            wearApp.unwatch();
        }
    }
}
