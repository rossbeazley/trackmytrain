package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.CapturingPostman;
import uk.co.rossbeazley.trackmytrain.android.wear.CapturingServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.CapturingServiceViewNavigationController;
import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.StoppedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.TrackedServiceMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.WearApp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationsStartTests {


    @Test
    public void
    whenAllUIDetachesDuringTrackingNotificationServiceIsStarted() {


        CapturingNotificationServiceService service = new CapturingNotificationServiceService();

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman(), service);
        final CapturingServiceView anyView = new CapturingServiceView();
        CapturingServiceViewNavigationController anyNavigationController = new CapturingServiceViewNavigationController();

        wearApp.attach(anyView);
        wearApp.attach(anyNavigationController);
        Postman.NodeId anyId = new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);

        wearApp.detach(anyView);

        assertThat(service.state, is(CapturingNotificationServiceService.STARTED));

    }


    @Test
    public void
    whenAllUIDetachesAfterTrackingHasStopped_NONotificationServiceIsStarted() {

        CapturingNotificationServiceService service = new CapturingNotificationServiceService();

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman(), service);
        final CapturingServiceView anyView = new CapturingServiceView();
        wearApp.attach(anyView);
        Postman.NodeId anyId = new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);

        wearApp.message(new MessageEnvelope(anyId, new StoppedTrackingMessage()));

        wearApp.detach(anyView);

        assertThat(service.state, is(CapturingNotificationServiceService.UNKNOWN));
    }


    @Test
    public void
    whenTrackingHasStartedWithNoUIAttached_NONotificationServiceIsStarted() {

        CapturingNotificationServiceService service = new CapturingNotificationServiceService();

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman(), service);

        Postman.NodeId anyId = new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);

        wearApp.message(new MessageEnvelope(anyId, new StoppedTrackingMessage()));

        assertThat(service.state, is(CapturingNotificationServiceService.UNKNOWN));
    }


    @Test
    public void
    whenTrackedServiecUpdatesWithUIAttached_NONotificationServiceIsUpdated() {

        CapturingNotificationServiceService service = new CapturingNotificationServiceService();

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman(), service);

        Postman.NodeId anyId = new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);

        Train aTrain = new Train("anyId", "20:24", "20:22", "4", false);
        wearApp.message(new MessageEnvelope(anyId, new TrackedServiceMessage(aTrain)));

        assertThat(service.state, is(CapturingNotificationServiceService.UNKNOWN));
        assertThat(service.lastPresentedTrain, is(nullValue()));
    }



}
