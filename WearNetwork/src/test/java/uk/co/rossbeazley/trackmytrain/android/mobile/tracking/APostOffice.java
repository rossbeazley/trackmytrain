package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman.*;

public class APostOffice {

    @Test
    public void
    willGiveMessagesStraightToThePostmanWhenConnected() {
        final CapturingPostman capturingPostman = new CapturingPostman();
        final PostOffice postOffice = new PostOffice(capturingPostman, capturingPostman);

        final NodeId anyNode = new NodeId("anyNode");
        final String messagePath = "/any/message";
        Message expectedMessage = new Message(anyNode, messagePath);

        capturingPostman.connect();

        postOffice.post(expectedMessage);

        assertThat(capturingPostman.messagePosted, is(expectedMessage));
    }


    @Test
    public void
    willGiveBroadcastsStraightToThePostmanWhenConnected() {
        final CapturingPostman capturingPostman = new CapturingPostman();
        final PostOffice postOffice = new PostOffice(capturingPostman, capturingPostman);

        final String messagePath = "/any/message";
        BroadcastMessage expectedMessage = new BroadcastMessage(messagePath);

        capturingPostman.connect();

        postOffice.broadcast(expectedMessage);

        assertThat(capturingPostman.messageBroadcast, is(expectedMessage));
    }



    @Test
    public void
    willGiveMessagesToThePostmanWhenBecomingConnected() {
        final CapturingPostman capturingPostman = new CapturingPostman();
        final PostOffice postOffice = new PostOffice(capturingPostman, capturingPostman);

        final NodeId anyNode = new NodeId("anyNode");
        final String messagePath = "/any/message";
        Message expectedMessage = new Message(anyNode, messagePath);

        postOffice.post(expectedMessage);
        capturingPostman.connect();

        assertThat(capturingPostman.messagePosted, is(expectedMessage));
    }

    @Test
    public void
    willGiveBroadcastsToThePostmanWhenBecomingConnected() {
        final CapturingPostman capturingPostman = new CapturingPostman();
        final PostOffice postOffice = new PostOffice(capturingPostman, capturingPostman);

        final String messagePath = "/any/message";
        BroadcastMessage expectedMessage = new BroadcastMessage(messagePath);


        postOffice.broadcast(expectedMessage);
        capturingPostman.connect();

        assertThat(capturingPostman.messageBroadcast, is(expectedMessage));
    }



    private static class CapturingPostman implements Postman, Network {
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
}