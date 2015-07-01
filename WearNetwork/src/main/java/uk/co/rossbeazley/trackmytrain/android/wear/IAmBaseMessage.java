package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

class IAmBaseMessage extends Postman.BroadcastMessage {
    public static final String MESSAGE_PATH = "/I/AM/BASE";
    private final Postman.NodeId hostNodeId;

    public IAmBaseMessage(Postman.NodeId hostNodeId) {
        super(MESSAGE_PATH);
        this.hostNodeId = hostNodeId;
    }

    public Postman.NodeId hostNodeId() {
        return hostNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        IAmBaseMessage that = (IAmBaseMessage) o;

        return !(hostNodeId != null ? !hostNodeId.equals(that.hostNodeId) : that.hostNodeId != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (hostNodeId != null ? hostNodeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + "IAmBaseMessage{" +
                "hostNodeId=" + hostNodeId +
                '}';
    }

    static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            Postman.NodeId nodeId = new Postman.NodeId(messageEvent.getSourceNodeId());
            return new IAmBaseMessage(nodeId);
        }
    }
}
