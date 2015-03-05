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

    private String serviceId;
    private String scheduledTime;
    private String estimatedTime;

    private String platform;
    private ServiceDetailsRequest serviceDetailsRequest;
    private Map<NetworkClient.Request, String> map;
    private TrackMyTrain tmt;
    private TrainViewModel expectedTrain;

    private ControllableExecutorService ness;
    private CapturingServiceView serviceView;

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
        serviceView = new CapturingServiceView();

        ness = new ControllableExecutorService();

        tmt = TestDataBuilder.TMTBuilder()
                .with(client)
                .with(ness)
                .build();

        tmt.attach(serviceView);
    }

    @Test
    public void theOneWhereWeSelectAServiceAndTrackingStarts() {
        tmt.watch(serviceId);
        assertThat(serviceView.serviceDisplayed, is(expectedTrain));
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
        serviceView.serviceDisplayed=null;
        final Train train = new Train(serviceId, "20:52", scheduledTime, platform);
        final TrainViewModel expectedTrain = new TrainViewModel(train);
        map.put(serviceDetailsRequest, TestDataBuilder.jsonForTrain(train));
        ness.scheduledCommand.run();

        assertThat(serviceView.serviceDisplayed, is(expectedTrain));
    }

    @Test
    public void theOneWhereWeStopTracking() {
        tmt.watch(serviceId);
        ness.scheduledCommand.run();
        serviceView.serviceDisplayed=null;
        tmt.unwatch();
        ness.scheduledCommand.run();

        assertThat(serviceView.serviceDisplayed, is(nullValue()));
    }

    @Test
    public void theOneWhereTheServiceViewIsHidden() {
        tmt.watch(serviceId);
        ness.scheduledCommand.run();
        serviceView.serviceDisplayed=null;
        tmt.unwatch();
        ness.scheduledCommand.run();

        assertThat(serviceView.visibility, is(serviceView.HIDDEN));
    }

    @Test
    public void theOneWhereTheTimerIsStopped() {
        tmt.watch(serviceId);
        ness.scheduledCommand.run();
        tmt.unwatch();
        ness.scheduledCommand.run();

        assertThat(ness.cancelable, is(nullValue()));
    }

    public static class CapturingServiceView implements ServiceView {
        public static final String HIDDEN = "Hidden";
        public static final String VISIBLE = "Visible";
        public String visibility = "UNKNOWN";
        public TrainViewModel serviceDisplayed;

        @Override
        public void present(TrainViewModel train) {
            visibility = VISIBLE;
            serviceDisplayed = train;
        }

        @Override
        public void hide() {
            serviceDisplayed = null;
            visibility = HIDDEN;
        }
    }

    public static class ControllableExecutorService implements NarrowScheduledExecutorService {


        public NarrowScheduledExecutorService.Cancelable cancelable;
        public Runnable NO_COMMAND = new Runnable() {
            @Override
            public void run() {
            }
        };
        public Runnable scheduledCommand = NO_COMMAND;


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
    }
}
