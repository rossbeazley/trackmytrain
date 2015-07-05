package uk.co.rossbeazley.trackmytrain.android.wear;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearApp {
    private final HostNode hostNode;

    public WearApp(HostNode hostNode) {
        this.hostNode = hostNode;
    }

    public void message(MessageEnvelope messageEnvelope) {
        StartedTrackingMessage startedTrackingMessage = (StartedTrackingMessage) messageEnvelope.message();
        hostNode.register(messageEnvelope.fromId());
    }
}
