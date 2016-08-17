package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import fakes.*;
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

        TrackMyTrain tmt = fakes.TestDataBuilder.TMTBuilder()
                .with(new RequestMapNetworkClient(requestMap))
                .build();


        CapturingTrackedServiceListener trackedServiceListener = new CapturingTrackedServiceListener();
        tmt.addTrackedServiceListener(trackedServiceListener);
        tmt.watchService(serviceId);


        assertThat(trackedServiceListener.tracking, is(CapturingTrackedServiceListener.STOPPED));

    }


}
