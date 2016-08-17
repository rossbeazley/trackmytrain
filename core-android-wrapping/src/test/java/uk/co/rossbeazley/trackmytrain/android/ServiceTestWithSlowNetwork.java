package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import fakes.*;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ServiceTestWithSlowNetwork {

    private String serviceId;
    private Map<NetworkClient.Request, String> map;

    private ControllableExecutorService ness;
    private SlowRequestMapNetworkClient client;
    private TrackMyTrain tmt;

    @Before
    public void setUp() throws Exception {
        final Train train = fakes.TestDataBuilder.anyTrain();
        serviceId = train.id;
        final String initialJson = fakes.TestDataBuilder.jsonForTrain(train);
        final ServiceDetailsRequest serviceDetailsRequest = new ServiceDetailsRequest(serviceId);
        map = new HashMap<NetworkClient.Request, String>() {{
            put(serviceDetailsRequest, initialJson);
        }};
        client = new SlowRequestMapNetworkClient(map);

        ness = new ControllableExecutorService();

        tmt = fakes.TestDataBuilder.TMTBuilder()
                .with(client)
                .with(ness)
                .build();

    }

    @Test
    public void
    stoppingTrackingWhilstARefreshIsHappening() {

        CapturingTrackedServiceListener trackedServiceListener = new CapturingTrackedServiceListener();
        tmt.addTrackedServiceListener(trackedServiceListener);

        tmt.watchService(serviceId);
        ness.scheduledCommand.run();
        tmt.unwatchService();
        client.completeRequest();

        assertThat(trackedServiceListener.tracking, is(CapturingTrackedServiceListener.STOPPED));
    }

}

