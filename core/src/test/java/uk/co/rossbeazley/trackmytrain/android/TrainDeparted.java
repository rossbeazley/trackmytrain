package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrainDeparted {

    @Test
    public void
    trackingADepartedTrain() {

        final String serviceId = "1";


        Map<NetworkClient.Request, String> requestMap = new HashMap<>();
        String resultJson;
        resultJson = "{\n" +
                "\"id\": \"1\",\n" +
                "\"scheduledTime\": \"Departed\",\n" +
                "\"estimatedTime\": \"17:58\",\n" +
                "\"platform\": \"\",\n" +
                "\"departed\": true\n" +
                "}";

        requestMap.put(new ServiceDetailsRequest(serviceId), resultJson);

        TrackMyTrain tmt = TestDataBuilder.TMTBuilder()
                .with(new RequestMapNetworkClient(requestMap))
                .build();

        final ServiceTest.CapturingServiceView serviceView = new ServiceTest.CapturingServiceView();
        tmt.attach(serviceView);

        tmt.watch(serviceId);

        assertThat(serviceView.visibility, is(ServiceTest.CapturingServiceView.HIDDEN));

    }


}
