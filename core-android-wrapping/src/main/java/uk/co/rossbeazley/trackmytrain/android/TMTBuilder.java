package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.DefaultNarrowScheduledExecutorService;
import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesPerformanceMonitoring;
import uk.co.rossbeazley.trackmytrain.android.departures.StationRepository;
import uk.co.rossbeazley.trackmytrain.android.trackedService.Tracking;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackingAnalytics;
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

        Tracking tracking = createTrackingJavaSubModule(this.networkClient, this.executorService);
        DepartureQueries departureQueries = createDepartureQueriesJavaSubModule(this.networkClient, this.keyValuePersistence);

        TrackMyTrain trackMyTrain = new TrackMyTrain(tracking, departureQueries);

        trackMyTrain.addDepartureQueryListener(new DeparturesPerformanceMonitoring(analytics, clock));
        trackMyTrain.addTrackedServiceListener(new TrackingAnalytics(analytics));

        return trackMyTrain;
    }

    public DepartureQueries createDepartureQueriesJavaSubModule(NetworkClient networkClient, KeyValuePersistence keyValuePersistence) {
        return new DepartureQueries(new StationRepository(keyValuePersistence), networkClient);
    }

    public Tracking createTrackingJavaSubModule(NetworkClient networkClient, NarrowScheduledExecutorService executorService) {
        return new Tracking(executorService, networkClient);
    }


    public Tracking createTrackingJNISubModule(NetworkClient networkClient, NarrowScheduledExecutorService executorService) {
        return null;
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
