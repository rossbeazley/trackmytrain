package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import java.util.ArrayList;

public class CapturingPostman implements Postman, Network {
    public Message messagePosted;
    public Message messageBroadcast;
    private ArrayList<Connection> connectionListeners = new ArrayList<>();

    @Override
    public void post(Message message, NodeId deliveryAddress) {
        messagePosted = message;
    }

    @Override
    public void broadcast(Message message) {
        messageBroadcast=message;
    }

    @Override
    public void register(Connection listener) {
        this.connectionListeners.add(listener);
    }
    @Override
    public void deregister(Connection listener) {
        this.connectionListeners.remove(listener);
    }

    public void connect() {
        messageBroadcast=null;
        messagePosted=null;
        for (Connection connectionListener : connectionListeners) {
            connectionListener.connected();
        }

    }

    public void disconnect() {
        for (Connection connectionListener : connectionListeners) {
            connectionListener.disconnected();
        }

    }

}
