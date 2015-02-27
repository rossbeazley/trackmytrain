package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
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
    private Train expectedTrain;

    private NarrowScheduledExecutorService ness;
    private NarrowScheduledExecutorService.Cancelable cancelable;
    private Runnable NO_COMMAND = new Runnable() {
        @Override
        public void run() {
        }
    };
    private Runnable scheduledCommand = NO_COMMAND;

    @Before
    public void setUp() throws Exception {
        serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        scheduledTime = "20:48";
        estimatedTime = "On time";
        platform = "2";
        expectedTrain = new Train(serviceId, estimatedTime, scheduledTime, platform);
        final String initialJson = TestDataBuilder.jsonForTrain(expectedTrain);
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

        ness = new NarrowScheduledExecutorService() {
            @Override
            public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
                scheduledCommand = command;
                cancelable = new Cancelable() {
                    @Override
                    public void cancel() {
                        cancelable = null;
                        scheduledCommand = NO_COMMAND;
                    }
                };
                return cancelable;
            }
        };

        tmt = new TMTBuilder()
                .with(client)
                .with(ness)
                .build();

        tmt.attach(serviceView);
    }

    @Test
    public void theOneWhereWeSelectAServiceAndTrackingStarts() {
        tmt.watch(serviceId);
        assertThat(serviceDisplayed, is(expectedTrain));
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
        final Train expectedTrain = new Train(serviceId, "20:52", scheduledTime, platform);
        map.put(serviceDetailsRequest, TestDataBuilder.jsonForTrain(expectedTrain));
        scheduledCommand.run();

        assertThat(serviceDisplayed, is(expectedTrain));
    }

    @Test
    public void theOneWhereWeStopTracking() {
        tmt.watch(serviceId);
        scheduledCommand.run();
        serviceDisplayed=null;
        tmt.unwatch();
        scheduledCommand.run();

        assertThat(serviceDisplayed, is(nullValue()));
    }

    @Test
    public void theOneWhereTheServiceViewIsHidden() {
        tmt.watch(serviceId);
        scheduledCommand.run();
        serviceDisplayed=null;
        tmt.unwatch();
        scheduledCommand.run();

        assertThat(serviceView, is(HIDDEN));
    }

    @Test
    public void theOneWhereTheTimerIsStopped() {
        tmt.watch(serviceId);
        scheduledCommand.run();
        tmt.unwatch();
        scheduledCommand.run();

        assertThat(cancelable, is(nullValue()));
    }

}
