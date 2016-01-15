package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;

public interface WearNotificationService {

    void show();

    void hide();

    interface WearNotification {
        void show(TrainViewModel trainViewModel);
    }
}
