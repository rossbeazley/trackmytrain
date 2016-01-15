package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationsHaveStartedTests {

    private CapturingNotificationServiceService service;
    private WearApp wearApp;
    private CapturingNotification notificationPresenter;

    @Before
    public void startNotificationService() {

        service = new CapturingNotificationServiceService();

        HostNode hostNode = new HostNode();
        wearApp = new WearApp(hostNode, new CapturingPostman(), service);
        final CapturingServiceView anyView = new CapturingServiceView();
        wearApp.attach(anyView);
        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);

        wearApp.detach(anyView);

        assertThat(service.state, is(CapturingNotificationServiceService.STARTED));

        notificationPresenter = new CapturingNotification();
        wearApp.attach(notificationPresenter);

    }

    @Test
    public void theServiceDetailsAreUpdated() {

        Train expectedTrain = new Train("anyId", "20:24", "20:22", "4", false);

        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new TrackedServiceMessage(expectedTrain));
        wearApp.message(message);


        TrainViewModel expectedTrainViewModel = new TrainViewModel(expectedTrain);
        assertThat(notificationPresenter.lastPresentedTrain,is(expectedTrainViewModel));
    }

    @Test
    public void theServiceDetailsAreUpdatedAfterServiceDetaches() {

        wearApp.detach(notificationPresenter);

        Train expectedTrain = new Train("anyId", "20:24", "20:22", "4", false);

        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new TrackedServiceMessage(expectedTrain));
        wearApp.message(message);

        assertThat(notificationPresenter.lastPresentedTrain,is(nullValue()));
    }


    @Test
    public void theServiceDetailsAreUpdatedAgain() {

        Train expectedTrain = new Train("anyId", "20:24", "20:28", "4", false);


        Train aTrain = new Train("anyId", "20:24", "20:22", "4", false);
        Postman.NodeId anyId =  new Postman.NodeId("anyId");
        wearApp.message(new MessageEnvelope(anyId, new TrackedServiceMessage(aTrain)));


        wearApp.message(new MessageEnvelope(anyId, new TrackedServiceMessage(expectedTrain)));


        TrainViewModel expectedTrainViewModel = new TrainViewModel(expectedTrain);
        assertThat(notificationPresenter.lastPresentedTrain,is(expectedTrainViewModel));
    }

    @Test
    public void theUIIsReattached_notifiactionStops() {
        wearApp.attach(new CapturingServiceView());

        assertThat(service.state,is(CapturingNotificationServiceService.STOPPED));
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
