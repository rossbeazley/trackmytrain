package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import fakes.RequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class LateObservationOfServiceTest {

    private String serviceId;

    private ServiceDetailsRequest serviceDetailsRequest;
    private TrainViewModel expectedTrain;
    private TrainViewModel presentedTrain;
    private TrackedServicePresenter trackedServicePresenter;

    @Before
    public void setUp() throws Exception {
        serviceId = "3Olk7M389Qp5JIdkXAQt4g==";
        String scheduledTime = "20:48";
        String estimatedTime = "On time";
        String platform = "2";
        final Train train = new Train(serviceId, estimatedTime, scheduledTime, platform, false);
        expectedTrain = new TrainViewModel(train);
        final String initialJson = TestDataBuilder.jsonForTrain(train);
        serviceDetailsRequest = new ServiceDetailsRequest(serviceId);
        Map<NetworkClient.Request, String> map = new HashMap<NetworkClient.Request, String>() {{
            put(serviceDetailsRequest, initialJson);
        }};
        NetworkClient client = new RequestMapNetworkClient(map);

        NarrowScheduledExecutorService ness = new NarrowScheduledExecutorService() {
            @Override
            public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
                command.run();
                Cancelable cancelable = new Cancelable() {
                    @Override
                    public void cancel() {
                    }
                };
                return cancelable;
            }
        };

        TrackMyTrain tmt = TestDataBuilder.TMTBuilder()
                .with(client)
                .with(ness)
                .build();

        trackedServicePresenter = new TrackedServicePresenter(tmt);
    }

    @Test
    public void lateObservationOfWatchedTrain() {
        trackedServicePresenter.watch(serviceId);

        trackedServicePresenter.attach(new ServiceView() {
            @Override
            public void present(TrainViewModel train) {
                presentedTrain = train;
            }

            @Override
            public void hide() {

            }

            @Override
            public void trackingStarted() {

            }
        });

        assertThat(presentedTrain,is(expectedTrain));
    }

}
