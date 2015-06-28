package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wearnetwork.HostNode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WearableListenerServiceWill {

    @Test public void
    registerHostNodeIDWhenHostBroadcastsSelf() {
        Postman.NodeId expectedId = new Postman.NodeId("any id");

        SyncHostNode hostNode  = new SyncHostNode();
        WearApp wearApp = new WearApp(hostNode);

        Postman.BroadcastMessage iAmBaseMessage= new IAmBaseMessage(expectedId);
        wearApp.message(iAmBaseMessage);

        assertThat(hostNode.registeredId, is(expectedId));
    }


    private static class SyncHostNode implements HostNode {
        public Postman.NodeId registeredId;

        @Override
        public void id(Result result) {
            result.id(registeredId);
        }

        @Override
        public void register(Postman.NodeId nodeId) {
            registeredId=nodeId;
        }
    }

    public class TrackMyTrainMessageService extends WearableListenerService {

        // just forwards calls to the WearApp instance

        @Override
        public void onMessageReceived(MessageEvent messageEvent) {

            Postman.Message message = new PostmanMessageFactory().toMessage(messageEvent);
            new WearApp(null).message(message);

        }
    }

    private class WearApp {
        private final HostNode hostNode;

        public WearApp(HostNode hostNode) {
            this.hostNode = hostNode;
        }


        public void message(Postman.Message message) {

        }

        public void message(Postman.BroadcastMessage message) {
            IAmBaseMessage iAmBaseMessage = (IAmBaseMessage) message;
            hostNode.register(iAmBaseMessage.hostNodeId());
        }
    }

    private class PostmanMessageFactory {
        public Postman.Message toMessage(MessageEvent messageEvent) {
            return null;
        }
    }

    private class IAmBaseMessage extends Postman.BroadcastMessage {
        private final Postman.NodeId hostNodeId;

        public IAmBaseMessage(Postman.NodeId hostNodeId) {
            super("/I/AM/BASE");
            this.hostNodeId = hostNodeId;
        }

        public Postman.NodeId hostNodeId() {
            return hostNodeId;
        }
    }
}
