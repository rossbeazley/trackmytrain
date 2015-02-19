package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ServiceTest {

    private static final String HIDDEN = "Hidden";
    private static final String VISIBLE = "Visible";
    private String serviceView = "UNKNOWN";
    private Train serviceDisplayed;

    private String serviceId;
    private String scheduledTime;
    private String estimatedTime;

    private String platform;
    private ServiceDetailsRequest serviceDetailsRequest;
    private Map<NetworkClient.Request, String> map;
    private TrackMyTrain tmt;

    @Before
    public void setUp() throws Exception {
        serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        scheduledTime = "20:48";
        estimatedTime = "On time";
        platform = "2";
        final String initialJson = jsonForTrain(serviceId, scheduledTime, estimatedTime, platform);
        serviceDetailsRequest = new ServiceDetailsRequest(serviceId);
        map = new HashMap<NetworkClient.Request, String>(){{
            put(serviceDetailsRequest, initialJson);
        }};
        NetworkClient client = new RequestMapNetworkClient(map);
        ServiceView serviceView = new ServiceView() {
            @Override
            public void present(Train train) {
                ServiceTest.this.serviceView = VISIBLE;
                serviceDisplayed = train;
            }

            @Override
            public void hide() {
                serviceDisplayed = null;
                ServiceTest.this.serviceView = HIDDEN;
            }
        };

        tmt = new TMTBuilder()
                .with(client)
                .with(serviceView)
                .build();
    }

    @Test
    public void theOneWhereWeSelectAServiceAndTrackingStarts() {
        tmt.watch(serviceId);

        assertThat(serviceDisplayed, is(new Train(serviceId, estimatedTime, scheduledTime, platform)));
    }

    private String jsonForTrain(String serviceId, String scheduledTime, String estimatedTime, String platform) {
        return "{\n" +
                "\"id\": \"" + serviceId + "\",\n" +
                "\"scheduledTime\": \"" + scheduledTime + "\",\n" +
                "\"estimatedTime\": \"" + estimatedTime + "\",\n" +
                "\"platform\": \"" + platform + "\"\n" +
                "}";
    }

    @Test
    public void serviceDetailsRequestRendersToString() {

        final String serviceUrl = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service/123456";
        assertThat(new ServiceDetailsRequest("123456").asUrlString(),is(serviceUrl));
    }

    @Test
    public void theOneWhereWeAreUpdatedAboutTheSelectedService() {
        tmt.watch(serviceId);
        serviceDisplayed=null;
        String lateTime = "20:52";
        map.put(serviceDetailsRequest,jsonForTrain(serviceId, scheduledTime, lateTime, platform));
        tmt.tick();

        assertThat(serviceDisplayed, is(new Train(serviceId, lateTime, scheduledTime, platform)));
    }

    @Test
    public void theOneWhereWeStopTracking() {
        tmt.watch(serviceId);
        tmt.tick();
        serviceDisplayed=null;
        tmt.unwatch();
        tmt.tick();

        assertThat(serviceDisplayed, is(nullValue()));
    }

    @Test
    public void theOneWhereTheServiceViewIsHidden() {
        tmt.watch(serviceId);
        tmt.tick();
        serviceDisplayed=null;
        tmt.unwatch();
        tmt.tick();

        assertThat(serviceView, is(HIDDEN));
    }

}
