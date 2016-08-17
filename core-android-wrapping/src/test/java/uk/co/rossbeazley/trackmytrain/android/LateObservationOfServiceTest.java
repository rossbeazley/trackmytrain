package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import fakes.*;
import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static fakes.CapturingTrackedServiceListener.STARTED;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LateObservationOfServiceTest {

    private String serviceId;

    private ServiceDetailsRequest serviceDetailsRequest;
    private TrackMyTrain tmt;
    private Train expectedTrain;
    private String jsonForTrain;

    @Before
    public void setUp() throws Exception {
        expectedTrain = fakes.TestDataBuilder.anyTrainNotDeparted();
        serviceId = expectedTrain.id;
        jsonForTrain = fakes.TestDataBuilder.jsonForTrain(expectedTrain);
        serviceDetailsRequest = new ServiceDetailsRequest(serviceId);
        Map<NetworkClient.Request, String> map = new HashMap<NetworkClient.Request, String>() {{
            put(serviceDetailsRequest, jsonForTrain);
        }};
        NetworkClient client = new RequestMapNetworkClient(map);

        NarrowScheduledExecutorService ness = new NarrowScheduledExecutorService() {
            @Override
            public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
                command.run();
                Cancelable cancelable = new Cancelable() {
                    @Override
                    public void cancel() {
                    }
                };
                return cancelable;
            }
        };

        tmt = fakes.TestDataBuilder.TMTBuilder()
                .with(client)
                .with(ness)
                .build();

    }

    @Test
    public void lateObservationOfWatchedTrain() {
        CapturingTrackedServiceListener capturingTrackedServiceListener = new CapturingTrackedServiceListener();
        tmt.watchService(serviceId);
        tmt.addTrackedServiceListener(capturingTrackedServiceListener);

        assertThat( "failed " + expectedTrain.toString() + ":::" + jsonForTrain, capturingTrackedServiceListener.tracking, is(STARTED));
    }

    @Test
    public void lateObservationOfWatchedTrainGetsFreshTrainData() {
        CapturingTrackedServiceListener capturingTrackedServiceListener = new CapturingTrackedServiceListener();
        tmt.watchService(serviceId);
        tmt.addTrackedServiceListener(capturingTrackedServiceListener);

        assertThat( "failed " + expectedTrain.toString() + ":::" + jsonForTrain, capturingTrackedServiceListener.train, is(expectedTrain));
    }

}
