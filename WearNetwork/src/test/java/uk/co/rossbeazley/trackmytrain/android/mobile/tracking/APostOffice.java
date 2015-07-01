package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import org.junit.Test;

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

        postOffice.post(expectedMessage, null);

        assertThat(capturingPostman.messagePosted, is(expectedMessage));
    }


    @Test
    public void
    willGiveBroadcastsStraightToThePostmanWhenConnected() {
        final CapturingPostman capturingPostman = new CapturingPostman();
        final PostOffice postOffice = new PostOffice(capturingPostman, capturingPostman);

        final String messagePath = "/any/message";
        Message expectedMessage = new BroadcastMessage(messagePath);

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

        postOffice.post(expectedMessage, null);
        capturingPostman.connect();

        assertThat(capturingPostman.messagePosted, is(expectedMessage));
    }

    @Test
    public void
    willGiveBroadcastsToThePostmanWhenBecomingConnected() {
        final CapturingPostman capturingPostman = new CapturingPostman();
        final PostOffice postOffice = new PostOffice(capturingPostman, capturingPostman);

        final String messagePath = "/any/message";
        Message expectedMessage = new BroadcastMessage(messagePath);


        postOffice.broadcast(expectedMessage);
        capturingPostman.connect();

        assertThat(capturingPostman.messageBroadcast, is(expectedMessage));
    }


}