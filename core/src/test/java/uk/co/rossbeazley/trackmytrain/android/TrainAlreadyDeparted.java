package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import fakes.CapturingServiceView;
import fakes.RequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrainAlreadyDeparted {

    @Test
    public void
    trackingADepartedTrain_trackingStops() {

        final String serviceId = "1";


        Map<NetworkClient.Request, String> requestMap = new HashMap<>();
        String resultJson;
        resultJson = "{\n" +
                "\"id\": \"" + serviceId + "\",\n" +
                "\"scheduledTime\": \"Departed\",\n" +
                "\"estimatedTime\": \"17:58\",\n" +
                "\"platform\": \"\",\n" +
                "\"departed\": true\n" +
                "}";

        requestMap.put(new ServiceDetailsRequest(serviceId), resultJson);

        TrackMyTrain tmt = TestDataBuilder.TMTBuilder()
                .with(new RequestMapNetworkClient(requestMap))
                .build();


        CapturingTrackedServiceListener trackedServiceListener = new CapturingTrackedServiceListener();
        tmt.addTrackedServiceListener(trackedServiceListener);
        tmt.watchService(serviceId);


        assertThat(trackedServiceListener.tracking, is(CapturingTrackedServiceListener.STOPPED));

    }


    private static class CapturingTrackedServiceListener implements CanTrackService.TrackedServiceListener {
        public static final String UNKNOWN = "UNKNOWN";
        public static final String STARTED = "STARTED";
        public static final String STOPPED = "STOPPED";
        public Train train;
        public String tracking = UNKNOWN;

        @Override
        public void trackingStarted() {
            tracking = STARTED;
        }

        @Override
        public void trackedServiceUpdated(Train train) {

            this.train = train;
        }

        @Override
        public void trackingStopped() {
            tracking = STOPPED;
        }
    }
}
