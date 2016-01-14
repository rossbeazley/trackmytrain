package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationsHaveStartedTests {

    private CapturingNotificationService service;
    private WearApp wearApp;

    @Before
    public void startNotificationService() {

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

    }

    @Test
    public void theServiceDetailsAreUpdated() {

        Train expectedTrain = new Train("anyId", "20:24", "20:22", "4", false);

        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new TrackedServiceMessage(expectedTrain));
        wearApp.message(message);


        TrainViewModel expectedTrainViewModel = new TrainViewModel(expectedTrain);
        assertThat(service.lastPresentedTrain,is(expectedTrainViewModel));
    }


    @Test
    public void theServiceDetailsAreUpdatedAgain() {

        Train expectedTrain = new Train("anyId", "20:24", "20:28", "4", false);


        Train aTrain = new Train("anyId", "20:24", "20:22", "4", false);
        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        wearApp.message(new MessageEnvelope(anyId, new TrackedServiceMessage(aTrain)));


        wearApp.message(new MessageEnvelope(anyId, new TrackedServiceMessage(expectedTrain)));


        TrainViewModel expectedTrainViewModel = new TrainViewModel(expectedTrain);
        assertThat(service.lastPresentedTrain,is(expectedTrainViewModel));
    }

    @Test
    public void theUIIsReattached_notifiactionStops() {
        wearApp.attach(new CapturingServiceView());

        assertThat(service.state,is(CapturingNotificationService.STOPPED));
    }

    @Test
    public void theUIIsReattachedWithServiceUpdate_notifiactionIsntUpdated() {
        wearApp.attach(new CapturingServiceView());

        Train aTrain = new Train("anyId", "20:24", "20:22", "4", false);
        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        wearApp.message(new MessageEnvelope(anyId, new TrackedServiceMessage(aTrain)));

        assertThat(service.lastPresentedTrain,is(nullValue()));
    }

}
