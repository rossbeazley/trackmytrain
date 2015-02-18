package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 17/02/2015.
 */
public class ServiceTest {

    private Train serviceDisplayed;

    @Test
    public void theOneWhereWeSelectAServiceAndTrackingStarts() {

        final String serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        Map<NetworkClient.Request, String> map = new HashMap<NetworkClient.Request, String>(){{

            put(new ServiceDetailsRequest(serviceId),"{\n" +
                    "\"id\": \"" + serviceId + "\",\n" +
                    "\"scheduledTime\": \"20:48\",\n" +
                    "\"estimatedTime\": \"On time\",\n" +
                    "\"platform\": \"2\"\n" +
                    "}");
        }};
        NetworkClient client = new RequestMapNetworkClient(map);
        ServiceView serviceView = new ServiceView() {
            @Override public void present(Train train) {
                serviceDisplayed = train;
            }
        };
        TrackMyTrain tmt = new TMTBuilder()
                .with(client)
                .with(serviceView)
                .build();

        tmt.watch(serviceId);

        assertThat(serviceDisplayed, is(new Train(serviceId, "On time", "20:48", "2")));

    }

}
