package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class MessageEnvelope {
    private final Postman.NodeId fromId;
    private final Postman.Message message;

    public MessageEnvelope(Postman.NodeId fromId, Postman.Message message) {
        this.fromId = fromId;
        this.message = message;
    }

    public Postman.NodeId fromId() {
        return fromId;
    }

    public Postman.Message message() {
        return message;
    }
}
