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
    private TrainViewModel serviceDisplayed;

    private String serviceId;
    private String scheduledTime;
    private String estimatedTime;

    private String platform;
    private ServiceDetailsRequest serviceDetailsRequest;
    private Map<NetworkClient.Request, String> map;
    private TrackMyTrain tmt;
    private TrainViewModel expectedTrain;

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
        final Train train = new Train(serviceId, estimatedTime, scheduledTime, platform);
        expectedTrain = new TrainViewModel(train);
        final String initialJson = TestDataBuilder.jsonForTrain(train);
        serviceDetailsRequest = new ServiceDetailsRequest(serviceId);
        map = new HashMap<NetworkClient.Request, String>(){{
            put(serviceDetailsRequest, initialJson);
        }};
        NetworkClient client = new RequestMapNetworkClient(map);
        ServiceView serviceView = new ServiceView() {
            @Override
            public void present(TrainViewModel train) {
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

        tmt = TestDataBuilder.TMTBuilder()
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
        final String serviceUrl = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service?id=123456";
        assertThat(new ServiceDetailsRequest("123456").asUrlString(),is(serviceUrl));
    }

    @Test
    public void serviceDetailsEncodesID() {
        final String serviceUrl = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service?id=123%2F456";
        assertThat(new ServiceDetailsRequest("123/456").asUrlString(),is(serviceUrl));
    }

    @Test
    public void theOneWhereWeAreUpdatedAboutTheSelectedService() {
        tmt.watch(serviceId);
        serviceDisplayed=null;
        final Train train = new Train(serviceId, "20:52", scheduledTime, platform);
        final TrainViewModel expectedTrain = new TrainViewModel(train);
        map.put(serviceDetailsRequest, TestDataBuilder.jsonForTrain(train));
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
