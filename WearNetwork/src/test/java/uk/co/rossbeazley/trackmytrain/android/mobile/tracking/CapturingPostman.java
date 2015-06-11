package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import java.util.ArrayList;

class CapturingPostman implements Postman, Network {
    public Message messagePosted;
    public BroadcastMessage messageBroadcast;
    private ArrayList<Connection> connectionListeners = new ArrayList<>();

    @Override
    public void post(Message message) {
        messagePosted = message;
    }

    @Override
    public void broadcast(BroadcastMessage message) {
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
