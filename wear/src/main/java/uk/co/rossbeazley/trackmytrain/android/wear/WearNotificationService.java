package uk.co.rossbeazley.trackmytrain.android.wear;

public interface WearNotificationService {

    void show();

    void hide();

    interface WearNotification {
        void show(TrainViewModel trainViewModel);
    }
}
