package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.CapturingNotificationServiceService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WearableListenerServiceWill {

    @Test public void
    registerHostNodeIDWhenHostSendsMessage() {
        Postman.NodeId expectedId = new Postman.NodeId("any id");

        HostNode hostNode  = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman(), new CapturingNotificationServiceService());

        Postman.Message iAmBaseMessage = new Postman.Message(null);
        final MessageEnvelope messageEnvelope = new MessageEnvelope(expectedId, iAmBaseMessage);
        wearApp.message(messageEnvelope);

        final MyResult result = new MyResult();
        hostNode.id(result);

        assertThat(result.actualId, is(expectedId));
    }

    private static class MyResult implements HostNode.Result {
        private Postman.NodeId actualId;

        @Override
        public void id(Postman.NodeId id) {
            actualId = id;
        }
    }


}
