package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class PostmanMessageFactory {

    private Map<String, MessageFactory> factoriesFromPath;

    public PostmanMessageFactory() {
        factoriesFromPath = new HashMap<>();
        factoriesFromPath.put(StartedTrackingMessage.MESSAGE_PATH, new StartedTrackingMessage.Factory());
        factoriesFromPath.put(StoppedTrackingMessage.MESSAGE_PATH, new StoppedTrackingMessage.Factory());
        factoriesFromPath.put(StopTrackingMessage.MESSAGE_PATH, new StopTrackingMessage.Factory());
        factoriesFromPath.put(TrackedServiceMessage.MESSAGE_PATH, new TrackedServiceMessage.Factory());
        factoriesFromPath.put(AnalyticsEventMessage.MESSAGE_PATH, new AnalyticsEventMessage.Factory());
    }

    public MessageEnvelope toMessage(MessageEvent messageEvent) {
        final String path = messageEvent.getPath();
        final MessageFactory messageFactory = messageFactoryForPath(path);
        Postman.NodeId nodeId = new Postman.NodeId(messageEvent.getSourceNodeId());
        return new MessageEnvelope(nodeId, messageFactory.create(messageEvent));
    }

    MessageFactory messageFactoryForPath(String path) {
        return factoriesFromPath.containsKey(path) ? factoriesFromPath.get(path) : searchForSubPath(path);
    }

    private MessageFactory searchForSubPath(String path) {
        MessageFactory messageFactory = null;
        for (String key : factoriesFromPath.keySet()) {
            if (path.startsWith(key)) {
                messageFactory = factoriesFromPath.get(key);
            }
        }
        return messageFactory;
    }

    public static interface MessageFactory {
        Postman.Message create(MessageEvent messageEvent);
    }
}
