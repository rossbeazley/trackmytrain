package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ServiceTestWithSlowNetwork {

    private String serviceId;
    private String scheduledTime;
    private String estimatedTime;

    private String platform;
    private ServiceDetailsRequest serviceDetailsRequest;
    private Map<NetworkClient.Request, String> map;
    private CanTrackTrains tmt;
    private TrainViewModel expectedTrain;

    private ControllableExecutorService ness;
    private CapturingServiceView serviceView;
    private DeparturesTest.SlowRequestMapNetworkClient client;

    @Before
    public void setUp() throws Exception {
        serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        scheduledTime = "20:48";
        estimatedTime = "On time";
        platform = "2";
        final Train train = new Train(serviceId, estimatedTime, scheduledTime, platform, false);
        expectedTrain = new TrainViewModel(train);
        final String initialJson = TestDataBuilder.jsonForTrain(train);
        serviceDetailsRequest = new ServiceDetailsRequest(serviceId);
        map = new HashMap<NetworkClient.Request, String>() {{
            put(serviceDetailsRequest, initialJson);
        }};
        client = new DeparturesTest.SlowRequestMapNetworkClient(map);
        serviceView = new CapturingServiceView();


        ness = new ControllableExecutorService();

        tmt = TestDataBuilder.TMTBuilder()
                .with(client)
                .with(ness)
                .build();

        tmt.attach(serviceView);
    }

    @Test
    public void
    stoppingTrackingWhilstARefreshIsHappening() {
        tmt.watch(serviceId);
        ness.scheduledCommand.run();
        tmt.unwatch();
        client.completeRequest();

        assertThat(serviceView.visibility, is(CapturingServiceView.HIDDEN));
    }

    public static class CapturingServiceView implements ServiceView {
        public static final String HIDDEN = "Hidden";
        public static final String VISIBLE = "Visible";
        public String visibility = "UNKNOWN";
        public TrainViewModel serviceDisplayed;

        public static final String STARTED = "Started";
        public String trackingIs = "UNKNOWN";

        @Override
        public void present(TrainViewModel train) {
            visibility = VISIBLE;
            serviceDisplayed = train;
        }

        @Override
        public void hide() {
            serviceDisplayed = null;
            visibility = HIDDEN;
            trackingIs = HIDDEN;
        }

        @Override
        public void trackingStarted() {
            trackingIs = STARTED;
        }
    }

    public static class ControllableExecutorService implements NarrowScheduledExecutorService {


        public Cancelable cancelable;
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

