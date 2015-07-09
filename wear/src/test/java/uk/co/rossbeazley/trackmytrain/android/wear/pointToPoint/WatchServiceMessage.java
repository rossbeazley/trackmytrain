package uk.co.rossbeazley.trackmytrain.android.wear.pointToPoint;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class WatchServiceMessage {

    public static Postman.Message createWatchServiceMessage(Postman.NodeId nodeId) {
        return new Postman.Message(nodeId,"");
    }
}
