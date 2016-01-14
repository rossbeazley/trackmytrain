package uk.co.rossbeazley.trackmytrain.android.wear;

public class CapturingNotificationService implements WearNotification {
    public final static String STARTED = "STARTED";
    public final static String UNKNOWN = "UNKNOWN";
    public static final String STOPPED = "STOPPED";

    public String state = UNKNOWN;
    public TrainViewModel lastPresentedTrain;

    @Override
    public void show() {
        state = STARTED;
    }

    @Override
    public void show(TrainViewModel trainViewModel) {
        lastPresentedTrain = trainViewModel;
    }

    @Override
    public void hide() {
        state = STOPPED;
    }

}
