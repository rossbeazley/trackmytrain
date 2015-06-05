package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.content.Context;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

/**
 * Created by beazlr02 on 30/05/2015.
 */
public class WearableTrackingNavigationController implements ServiceView {


    private final WearNetwork wearableNetwork;
    public final WearPostman wearPostman;

    public WearableTrackingNavigationController(Context context) {
        wearableNetwork = new WearNetwork(context);
        wearPostman = new WearPostman(wearableNetwork);
    }

    @Override
    public void present(TrainViewModel train) {
        wearPostman.broadcast("/tracking/start");
    }

    @Override
    public void hide() {
        wearPostman.broadcast("/tracking/stop");
    }


}
