package uk.co.rossbeazley.trackmytrain.android.wear;

public class CapturingNotification implements WearNotificationService.WearNotification {

    public TrainViewModel lastPresentedTrain;

    @Override
    public void show(TrainViewModel trainViewModel) {
        lastPresentedTrain = trainViewModel;
    }

}
