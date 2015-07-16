package uk.co.rossbeazley.trackmytrain.android.wear.pointToPoint;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.ServiceTest;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class A_TrackedServiceProxy {

    @Test
    public void
    willDispatchMessageWhenServiceWatched() {
        CapturingPostman postman = new CapturingPostman();
        final Postman.NodeId anyNodeId = new Postman.NodeId("anyNodeId");
        HostNode hostNode = new HostNode();
        hostNode.register(anyNodeId);

        TrackedServiceProxy trackedServiceProxy = new TrackedServiceProxy(postman,hostNode);

        ServiceView anyServiceView = new ServiceTest.CapturingServiceView();
        trackedServiceProxy.attach(anyServiceView);

        Postman.Message watchServiceMessage = WatchServiceMessage.createWatchServiceMessage();
        assertThat(postman.messageDelivered,is(equalTo(watchServiceMessage)));
    }

    @Test
    public void
    willDispatchMessageWhenServiceWatchedToHostNodeOnly() {
        CapturingPostman postman = new CapturingPostman();
        final Postman.NodeId anyNodeId = new Postman.NodeId("anyNodeId");
        HostNode hostNode = new HostNode();
        hostNode.register(anyNodeId);

        TrackedServiceProxy trackedServiceProxy = new TrackedServiceProxy(postman, hostNode);

        ServiceView anyServiceView = new ServiceTest.CapturingServiceView();
        trackedServiceProxy.attach(anyServiceView);

        assertThat(postman.deliveryAddress, is(equalTo(anyNodeId)));
    }

    private static class CapturingPostman implements Postman {
        public Message messageDelivered;
        public NodeId deliveryAddress;

        @Override
        public void post(Message message, NodeId deliveryAddress) {
            messageDelivered = message;
            this.deliveryAddress = deliveryAddress;
        }

        @Override
        public void broadcast(Message message) {

        }
    }

}