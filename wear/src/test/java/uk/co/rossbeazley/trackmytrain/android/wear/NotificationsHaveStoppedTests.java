package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationsHaveStoppedTests {

    private CapturingNotificationService service;
    private WearApp wearApp;

    @Before
    public void stoppedNotificationService() {

        service = new CapturingNotificationService();

        HostNode hostNode = new HostNode();
        wearApp = new WearApp(hostNode, new CapturingPostman(), service);
        final CapturingServiceView anyView = new CapturingServiceView();
        wearApp.attach(anyView);
        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);

        wearApp.detach(anyView);

        assertThat(service.state, is(CapturingNotificationService.STARTED));

        wearApp.message(new MessageEnvelope(anyId,new StoppedTrackingMessage()));
    }

    @Test
    public void theServiceDetailsAreUpdated() {
        assertThat(service.state,is(CapturingNotificationService.STOPPED));
    }



}
