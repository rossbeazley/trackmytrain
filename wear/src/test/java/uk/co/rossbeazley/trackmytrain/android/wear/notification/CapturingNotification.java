package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.WearNotificationService;

public class CapturingNotification implements WearNotificationService.WearNotification {

    public TrainViewModel lastPresentedTrain;

    @Override
    public void show(TrainViewModel trainViewModel) {
        lastPresentedTrain = trainViewModel;
    }

}
