package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import uk.co.rossbeazley.trackmytrain.android.CanTrackService;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.wear.TrackedServiceMessage;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.StoppedTrackingMessage;

public class MessagingTrackingPresenter implements CanTrackService.TrackedServiceListener {
    private final Postman postman;

    public MessagingTrackingPresenter(Postman postman) {
        this.postman = postman;
    }

    @Override
    public void trackingStarted() {
        postman.broadcast(new StartedTrackingMessage());

    }

    @Override
    public void trackedServiceUpdated(Train train) {
        postman.broadcast(new TrackedServiceMessage(new TrainViewModel(train)));
    }

    @Override
    public void trackingStopped() {
        postman.broadcast(new StoppedTrackingMessage());
    }

}
