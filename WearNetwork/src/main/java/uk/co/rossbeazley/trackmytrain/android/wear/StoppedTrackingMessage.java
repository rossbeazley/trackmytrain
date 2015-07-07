package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class StoppedTrackingMessage extends Postman.Message {
    public StoppedTrackingMessage() {
        super(null, "/TRACKING/STOPPED");
    }
}
