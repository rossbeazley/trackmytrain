package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

/**
 * Created by beazlr02 on 30/05/2015.
 */
public class WearableTrackingNavigationController implements ServiceView {


    private final Postman postman;

    public WearableTrackingNavigationController(Postman postman) {

        this.postman = postman;
    }

    @Override
    public void present(TrainViewModel train) {

        final Postman.BroadcastMessage trackingStartedBroadcastMessage;
        trackingStartedBroadcastMessage = TrackingStartedBroadcastMessage.createTrackingStartedBroadcastMessage();
        postman.broadcast(trackingStartedBroadcastMessage);
        //wearPostman.broadcast("/tracking/start");
    }

    @Override
    public void hide() {
        //wearPostman.broadcast("/tracking/stop");
    }


}
