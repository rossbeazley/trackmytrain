package uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen;

import uk.co.rossbeazley.trackmytrain.android.wear.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;

public class ExitTrackingScreen implements ServiceView {
    private final CanFinishWearApp canFinishWearApp;

    public ExitTrackingScreen(CanFinishWearApp canFinishWearApp) {

        this.canFinishWearApp = canFinishWearApp;
    }

    @Override
    public void present(TrainViewModel train) {

    }

    @Override
    public void hide() {
        canFinishWearApp.finish();
        WearAppSingleton.instance.detach(this);
    }

}
