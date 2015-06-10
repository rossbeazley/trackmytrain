package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import java.util.ArrayList;

class PostOffice implements Postman {
    private final Postman postman;
    private final Network network;
    private boolean connected;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<BroadcastMessage> broadcasts = new ArrayList<>();

    public PostOffice(final Postman postman, Network network) {
        this.postman = postman;
        this.network = network;
        network.register(new Network.Connection() {
            @Override
            public void connected() {
                connected=true;
                for (Message message : messages) {
                    postman.post(message);
                }
                for (BroadcastMessage broadcast : broadcasts) {
                    postman.broadcast(broadcast);
                }

            }

            @Override
            public void disconnected() {

            }
        });
    }

    @Override
    public void post(Message message) {
        if(connected) postman.post(message);
        else {
            enqueue(message);
        }

    }

    private void enqueue(Message message) {
        this.messages.add(message);
    }

    @Override
    public void broadcast(BroadcastMessage message) {
        if(connected) postman.broadcast(message);
        else {
            this.broadcasts.add(message);
        }
    }
}
