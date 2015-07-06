package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;

public class MessagingTrackingPresenter implements ServiceView {
    private final Postman postman;

    public MessagingTrackingPresenter(Postman postman) {
        this.postman = postman;
    }

    @Override
    public void present(TrainViewModel train) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void trackingStarted() {
        postman.broadcast(new StartedTrackingMessage());

    }

}
