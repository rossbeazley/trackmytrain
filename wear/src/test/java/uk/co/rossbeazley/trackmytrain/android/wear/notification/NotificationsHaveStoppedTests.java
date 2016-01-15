package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.CapturingPostman;
import uk.co.rossbeazley.trackmytrain.android.wear.CapturingServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.StoppedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.TrackedServiceMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.WearApp;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.CapturingNotificationServiceService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationsHaveStoppedTests {

    private CapturingNotificationServiceService service;
    private WearApp wearApp;

    @Before
    public void stoppedNotificationService() {

        service = new CapturingNotificationServiceService();

        HostNode hostNode = new HostNode();
        wearApp = new WearApp(hostNode, new CapturingPostman(), service);
        final CapturingServiceView anyView = new CapturingServiceView();
        wearApp.attach(anyView);
        Postman.NodeId anyId = sendStartedTrackingMessage();

        wearApp.detach(anyView);

        assertThat(service.state, is(CapturingNotificationServiceService.STARTED));

        wearApp.message(new MessageEnvelope(anyId,new StoppedTrackingMessage()));
    }

    @NonNull
    public Postman.NodeId sendStartedTrackingMessage() {
        Postman.NodeId anyId = anyNodeId();
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);
        return anyId;
    }

    @NonNull
    public Postman.NodeId anyNodeId() {
        return new Postman.NodeId("anyId");
    }

    @Test
    public void notificationIsStopped() {
        assertThat(service.state,is(CapturingNotificationServiceService.STOPPED));
    }


    @Test
    public void theServiceDetailsAreUpdated() {
        Train aTrain = new Train("anyId", "20:24", "20:22", "4", false);

        wearApp.message(new MessageEnvelope(anyNodeId(), new TrackedServiceMessage(aTrain)));

        assertThat(service.lastPresentedTrain,is(nullValue()));
    }


    @Test
    public void startingTrackingWithUpdatedServiceDetailsAndUIAttached_dosntUpdateNotifiction() {
        sendStartedTrackingMessage();
        wearApp.attach(new CapturingServiceView());

        Train aTrain = new Train("anyId", "20:24", "20:22", "4", false);

        wearApp.message(new MessageEnvelope(anyNodeId(), new TrackedServiceMessage(aTrain)));

        assertThat(service.state, is(CapturingNotificationServiceService.STOPPED));
        assertThat(service.lastPresentedTrain,is(nullValue()));
    }

}
