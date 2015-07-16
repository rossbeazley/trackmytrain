package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.ServiceTest;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackingAService {

    @Test
    public void
    announcesTrackingStarted() {


        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman());

        final ServiceTest.CapturingServiceView serviceView = new ServiceTest.CapturingServiceView();
        wearApp.attach(serviceView);


        Postman.NodeId anyId = new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StartedTrackingMessage());
        wearApp.message(message);

        assertThat(serviceView.trackingIs, is(ServiceTest.CapturingServiceView.STARTED));

    }

    @Test
    public void
    finishesWearAppWhenTrackingEnds() {

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman());

        final CapturingCanFinishWearApp capturingCanFinishWearApp = new CapturingCanFinishWearApp();
        wearApp.attach(new ExitWearApp(capturingCanFinishWearApp));

        Postman.NodeId anyId = new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StoppedTrackingMessage());
        wearApp.message(message);

        assertThat(capturingCanFinishWearApp.state, is(CapturingCanFinishWearApp.FINISHED));

    }

    @Test
    public void
    updatesViewWithTrackedServiceViewModel() {

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman());
        ServiceTest.CapturingServiceView serviceView = new ServiceTest.CapturingServiceView();
        wearApp.attach(serviceView);

        TrainViewModel expectedService = new TrainViewModel(new Train("2", "10:00", "09:00", "1"));
        wearApp.message(new MessageEnvelope(new Postman.NodeId("anyId"), new TrackedServiceMessage(expectedService)));

        assertThat(serviceView.serviceDisplayed, is(expectedService));
    }

    @Test
    public void
    startingAppAfterTrackingViewUpdatedWithCurrentService() {
        TrainViewModel expectedService = new TrainViewModel(new Train("2", "10:00", "09:00", "1"));

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman());
        ServiceTest.CapturingServiceView serviceView = new ServiceTest.CapturingServiceView();
        wearApp.message(new MessageEnvelope(new Postman.NodeId("anyId"), new TrackedServiceMessage(expectedService)));

        wearApp.attach(serviceView);

        assertThat(serviceView.serviceDisplayed, is(expectedService));
    }

    private static class CapturingCanFinishWearApp implements CanFinishWearApp {
        public static final String FINISHED = "finished";
        public String state = "UNKNOWN";

        @Override
        public void finish() {
            state = FINISHED;
        }
    }

}
