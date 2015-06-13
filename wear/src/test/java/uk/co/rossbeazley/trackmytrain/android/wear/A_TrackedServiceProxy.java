package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.CanTrackTrains;
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
        HostNode hostNode = new HostNode(){
            @Override
            public void id(Result result) {
                result.id(anyNodeId);
            }
        };

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

    private class TrackedServiceProxy implements CanTrackTrains{
        private final Postman postman;
        private final HostNode hostNode;

        public TrackedServiceProxy(Postman postman, HostNode hostNode) {

            this.postman = postman;
            this.hostNode = hostNode;
        }

        @Override
        public void watch(String serviceId) {

        }

        @Override
        public void unwatch() {

        }

        @Override
        public void attach(ServiceView serviceView) {
            hostNode.id(new HostNode.Result() {
                @Override
                public void id(Postman.NodeId id) {
                    postman.post(WatchServiceMessage.createWatchServiceMessage(id));
                }
            });

        }

        @Override
        public void detach(ServiceView serviceView) {

        }
    }

    private static class WatchServiceMessage {

        public static Postman.Message createWatchServiceMessage(Postman.NodeId nodeId) {
            return new Postman.Message(nodeId,"");
        }
    }

    interface HostNode {
        void id(Result result);

        interface Result {
            void id(Postman.NodeId id);
        }
    }
}