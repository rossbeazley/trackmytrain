package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import fakes.CapturingTrackedServiceListener;
import fakes.ControllableExecutorService;
import fakes.RequestMapNetworkClient;
import fakes.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrainDeparts {

    private TrackMyTrain tmt;
    private String serviceId;
    private StubNetworkClient networkClient;
    private ControllableExecutorService ness;
    private CapturingTrackedServiceListener trackedServiceListener;

    @Before
    public void trackingAService() {

        serviceId = "1";

        String trainJSON = "{\n" +
                "\"id\": \"" + serviceId + "\",\n" +
                "\"scheduledTime\": \"Departed\",\n" +
                "\"estimatedTime\": \"17:58\",\n" +
                "\"platform\": \"\"\n" +
                "}";



        networkClient = new StubNetworkClient();
        ness = new ControllableExecutorService();

        tmt = TestDataBuilder.TMTBuilder()
                .with(networkClient)
                .with(ness)
                .build();

        trackedServiceListener = new CapturingTrackedServiceListener();
        tmt.addTrackedServiceListener(trackedServiceListener);
        tmt.watchService(serviceId);

        networkClient.respondWith(trainJSON);

        assertThat(trackedServiceListener.tracking,is(CapturingTrackedServiceListener.STARTED));
    }

    @Test
    public void
    trainDeparts_trackingStops() {

        String departedTrainJSON = "{\n" +
                "\"id\": \"" + serviceId + "\",\n" +
                "\"scheduledTime\": \"Departed\",\n" +
                "\"estimatedTime\": \"17:58\",\n" +
                "\"platform\": \"\",\n" +
                "\"departed\": true\n" +
                "}";




        //update with departed json
        ness.scheduledCommand.run();
        networkClient.respondWith(departedTrainJSON);

        assertThat(trackedServiceListener.tracking, is(CapturingTrackedServiceListener.STOPPED));

    }


    private static class StubNetworkClient implements NetworkClient {
        private Response response;

        @Override
        public void get(Request request, Response response) {
            this.response = response;
        }

        public void respondWith(String trainJSON) {
            this.response.ok(trainJSON);
            this.response = null;
        }
    }
}
