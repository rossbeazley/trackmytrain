package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.ServiceTest;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackingAService {

    @Test
    public void
    announcesTrackingStarted() {


        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode);

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
        WearApp wearApp = new WearApp(hostNode);

        final CapturingCanFinishWearApp capturingCanFinishWearApp = new CapturingCanFinishWearApp();
        wearApp.attach(new ExitWearApp(capturingCanFinishWearApp));

        Postman.NodeId anyId = new Postman.NodeId("anyId");
        MessageEnvelope message = new MessageEnvelope(anyId, new StoppedTrackingMessage());
        wearApp.message(message);

        assertThat(capturingCanFinishWearApp.state, is(CapturingCanFinishWearApp.FINISHED));

    }

    private static class ExitWearApp implements ServiceView {
        private final CanFinishWearApp canFinishWearApp;

        public ExitWearApp(CanFinishWearApp canFinishWearApp) {

            this.canFinishWearApp = canFinishWearApp;
        }

        @Override
        public void present(TrainViewModel train) {

        }

        @Override
        public void hide() {
            canFinishWearApp.finish();
        }

        @Override
        public void trackingStarted() {

        }
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
