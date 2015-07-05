package uk.co.rossbeazley.trackmytrain.android.mobile;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;

class MessagingTrackingPresenter implements ServiceView {
    private final Postman postman;

    public MessagingTrackingPresenter(Postman postman) {
        this.postman = postman;
    }

    @Override
    public void present(TrainViewModel train) {
        action.run();
    }

    @Override
    public void hide() {
        action = announceTracking;
    }

    @Override
    public void trackingStarted() {

    }

    private Runnable announceTracking = new Runnable() {
        @Override
        public void run() {
            postman.broadcast(new StartedTrackingMessage());
            action = nothing;
        }
    };

    private Runnable nothing = new Runnable() {
        @Override
        public void run() {
        }
    };

    private Runnable action = announceTracking;

}
