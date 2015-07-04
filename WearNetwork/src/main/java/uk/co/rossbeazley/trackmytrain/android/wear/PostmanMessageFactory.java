package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

class PostmanMessageFactory {

    private Map<String, MessageFactory> factoriesFromPath;

    public PostmanMessageFactory() {
        factoriesFromPath = new HashMap<>();
        factoriesFromPath.put(IAmBaseMessage.MESSAGE_PATH, new IAmBaseMessage.Factory());
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
