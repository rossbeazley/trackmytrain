package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import fakes.CapturingServiceView;
import fakes.CapturingTrackedServiceListener;
import fakes.ControllableExecutorService;
import fakes.JournalingDepartureQueryListener;
import fakes.SlowRequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrackingAServiceTest {

    private String serviceId;
    private String scheduledTime;
    private String estimatedTime;

    private String platform;
    private TrackMyTrain tmt;
    private Train expectedTrain;
    private ControllableExecutorService ness;

    @Before
    public void setUp() throws Exception {
        serviceId = TestDataBuilder.anyTrainId();

        ness = new ControllableExecutorService();
        Map<NetworkClient.Request, String> hashmap = new HashMap<>();
        hashmap.put(new ServiceDetailsRequest(serviceId), TestDataBuilder.anyTrainJson());
        tmt = TestDataBuilder.TMTBuilder()
                .with(ness)
                .with(new SlowRequestMapNetworkClient(hashmap, true))
                .build();
    }

    @Test
    public void serviceDetailsRequestRendersToString() {
        final String serviceUrl = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service?id=123456";
        assertThat(new ServiceDetailsRequest("123456").asUrlString(),is(serviceUrl));
    }

    @Test
    public void serviceDetailsEncodesID() {
        final String serviceUrl = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service?id=123%2F456";
        assertThat(new ServiceDetailsRequest("123/456").asUrlString(),is(serviceUrl));
    }

    @Test
    public void continuedWatchingOfServiceDosntAnnounceTrackingStarted() {

        CapturingTrackedServiceListener trackedServiceListener = new CapturingTrackedServiceListener();
        tmt.addTrackedServiceListener(trackedServiceListener);

        tmt.watchService(serviceId);
        trackedServiceListener.tracking = "RESET"; //TODO implement reset method on fake

        ness.scheduledCommand.run();

        assertThat(trackedServiceListener.tracking, is("RESET"));
    }

    @Test @Ignore("core test")
    public void theOneWhereWeStopTracking() {

        CapturingTrackedServiceListener trackedServiceListener = new CapturingTrackedServiceListener();
        tmt.addTrackedServiceListener(trackedServiceListener);

        tmt.watchService(serviceId);

        ness.scheduledCommand.run();

        tmt.unwatchService();

        ness.scheduledCommand.run();

        assertThat(trackedServiceListener.tracking, is(CapturingTrackedServiceListener.STOPPED);
    }

    @Test  @Ignore("core test")
    public void theOneWhereTheTimerIsStopped() {

    }

}
