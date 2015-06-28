package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.ServiceTest;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class A_TrackedServiceProxy {

    @Test
    public void
    willDispatchMessageWhenServiceWatched() {
        CapturingPostman postman = new CapturingPostman();
        final Postman.NodeId anyNodeId = new Postman.NodeId("anyNodeId");
        SyncHostNode hostNode = new SyncHostNode();
        hostNode.register(anyNodeId);

        TrackedServiceProxy trackedServiceProxy = new TrackedServiceProxy(postman,hostNode);

        ServiceView anyServiceView = new ServiceTest.CapturingServiceView();
        trackedServiceProxy.attach(anyServiceView);

        Postman.Message watchServiceMessage = WatchServiceMessage.createWatchServiceMessage(anyNodeId);
        assertThat(postman.messageDelivered,is(equalTo(watchServiceMessage)));
    }

    private static class CapturingPostman implements Postman {
        public Message messageDelivered;

        @Override
        public void post(Message message) {
            messageDelivered = message;
        }

        @Override
        public void broadcast(BroadcastMessage message) {

        }
    }

}