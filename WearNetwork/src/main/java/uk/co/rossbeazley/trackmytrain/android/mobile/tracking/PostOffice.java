package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import java.util.ArrayList;
import java.util.List;

class PostOffice implements Postman {
    private final Postman postman;
    private final Network network;
    private boolean connected;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Message> broadcasts = new ArrayList<>();
    private List<NodeId> deliveryAddresss = new ArrayList<>();

    public PostOffice(final Postman postman, Network network) {
        this.postman = postman;
        this.network = network;
        network.register(new Network.Connection() {
            @Override
            public void connected() {
                connected=true;
                for (Message message : messages) {
                    postman.post(message, deliveryAddresss.get(messages.indexOf(message)));
                }
                for (Message broadcast : broadcasts) {
                    postman.broadcast(broadcast);
                }

            }

            @Override
            public void disconnected() {

            }
        });
    }

    @Override
    public void post(Message message, NodeId deliveryAddress) {
        if (connected) postman.post(message, deliveryAddress);
        else {
            enqueue(message, deliveryAddress);
        }

    }

    private void enqueue(Message message, NodeId deliveryAddress) {
        this.messages.add(message);
        this.deliveryAddresss.add(deliveryAddress);
    }

    @Override
    public void broadcast(Message message) {
        if(connected) postman.broadcast(message);
        else {
            this.broadcasts.add(message);
        }
    }
}
