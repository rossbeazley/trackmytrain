package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 17/02/2015.
 */
public class ServiceTest {

    private Train serviceDisplayed;

    @Test @Ignore("wip")
    public void theOneWhereWeSelectAServiceAndTrackingStarts() {


        HashMap<NetworkClient.Request, String> map = null;
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
        String serviceId = "12345";
        tmt.watch(serviceId);

        assertThat(serviceDisplayed, is(new Train(serviceId, "", "", "")));

    }

}
