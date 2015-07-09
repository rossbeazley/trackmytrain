package uk.co.rossbeazley.trackmytrain.android.mobile;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

/**
 * Created by beazlr02 on 09/07/2015.
 */
public class TrackedService extends Postman.BroadcastMessage {
    public TrackedService() {
        super("/TRACKED/SERVICE");
    }
}
