package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearApp {
    private final HostNode hostNode;

    public WearApp(HostNode hostNode) {
        this.hostNode = hostNode;
    }


    public void message(Postman.Message message) {
        IAmBaseMessage iAmBaseMessage = (IAmBaseMessage) message;
        hostNode.register(iAmBaseMessage.hostNodeId());
    }
}
