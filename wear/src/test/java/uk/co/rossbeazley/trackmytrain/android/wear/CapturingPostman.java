package uk.co.rossbeazley.trackmytrain.android.wear;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class CapturingPostman implements Postman {

    public List<Message> broadcasts = new ArrayList<>();

    @Override
    public void post(Message message, NodeId deliveryAddress) {
    }

    @Override
    public void broadcast(Message message) {
        this.broadcasts.add(message);
    }

    public void clearBroadcasts() {
        broadcasts.clear();
    }
}
