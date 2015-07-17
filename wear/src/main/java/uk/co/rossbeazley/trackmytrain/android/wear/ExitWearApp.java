package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

class ExitWearApp implements ServiceView {
    private final CanFinishWearApp canFinishWearApp;

    public ExitWearApp(CanFinishWearApp canFinishWearApp) {

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

    @Override
    public void trackingStarted() {

    }
}
