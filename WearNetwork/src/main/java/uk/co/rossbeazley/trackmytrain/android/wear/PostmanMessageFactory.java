package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.mobile.TrackedServiceMessage;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class PostmanMessageFactory {

    private Map<String, MessageFactory> factoriesFromPath;

    public PostmanMessageFactory() {
        factoriesFromPath = new HashMap<>();
        factoriesFromPath.put(StartedTrackingMessage.MESSAGE_PATH, new StartedTrackingMessage.Factory());
        factoriesFromPath.put(StoppedTrackingMessage.MESSAGE_PATH, new StoppedTrackingMessage.Factory());
        factoriesFromPath.put(TrackedServiceMessage.MESSAGE_PATH, new TrackedServiceMessage.Factory());
    }

    public MessageEnvelope toMessage(MessageEvent messageEvent) {
        final MessageFactory messageFactory = factoriesFromPath.get(messageEvent.getPath());
        Postman.NodeId nodeId = new Postman.NodeId(messageEvent.getSourceNodeId());
        return new MessageEnvelope(nodeId, messageFactory.create(messageEvent));
    }


    public static interface MessageFactory {
        Postman.Message create(MessageEvent messageEvent);
    }

}
