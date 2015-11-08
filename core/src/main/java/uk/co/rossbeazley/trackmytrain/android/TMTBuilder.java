package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.DefaultNarrowScheduledExecutorService;
import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.Clock;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.DeparturesPerformanceMonitoring;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.StringNetworkClient;

public class TMTBuilder {

    private NetworkClient networkClient;
    private NarrowScheduledExecutorService executorService;
    private KeyValuePersistence keyValuePersistence;
    private Analytics analytics;

    private Clock clock;

    public TMTBuilder() {
        networkClient = new StringNetworkClient();
        executorService = new DefaultNarrowScheduledExecutorService();
        keyValuePersistence = new HashMapKeyValuePersistence();
        analytics = new Analytics() {
            @Override
            public void timing(long millis, String category, String variable) {

            }

            @Override
            public void event(EventTrack eventTrack) {

            }

            @Override
            public void pageView(String pageName) {

            }
        };
        clock = new Clock() {
            @Override
            public long time() {
                return System.currentTimeMillis();
            }
        };
    }

    public TrackMyTrain build() {
        TrackMyTrain trackMyTrain = new TrackMyTrain(networkClient, executorService, keyValuePersistence);

        trackMyTrain.addDepartureQueryListener(new DeparturesPerformanceMonitoring(analytics, clock));
        return trackMyTrain;
    }

    public TMTBuilder with(NetworkClient networkClient) {
        this.networkClient = networkClient;
        return this;
    }

    public TMTBuilder with(NarrowScheduledExecutorService ness) {
        executorService = ness;
        return this;
    }

    public TMTBuilder with(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
        return this;
    }

    public TMTBuilder with(Analytics analytics) {
        this.analytics = analytics;
        return this;
    }

    public TMTBuilder with(Clock clock) {
        this.clock = clock;
        return this;
    }
}
