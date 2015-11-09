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

        TrackedServicePresenter trackedServicePresenter = new TrackedServicePresenter(tmt);

        final CapturingServiceView serviceView = new CapturingServiceView();
        trackedServicePresenter.attach(serviceView);

        trackedServicePresenter.watch(serviceId);

        assertThat(serviceView.visibility, is(CapturingServiceView.HIDDEN));

    }


}
