package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.departures.StationRepository;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;
import uk.co.rossbeazley.trackmytrain.android.trackedService.Tracking;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrain implements  CanTrackService, CanQueryDepartures {

    private final Tracking tracking;

    private DeparturesPresenter departuresPresenter;
    private final DepartureQueries departures;

    public TrackMyTrain(NetworkClient networkClient, NarrowScheduledExecutorService executorService, KeyValuePersistence keyValuePersistence) {
        TrainRepository trainRepository = new TrainRepository(networkClient);
        this.tracking = new Tracking(trainRepository, executorService);


        this.departures = new DepartureQueries(trainRepository, new StationRepository(keyValuePersistence));
        this.departuresPresenter = new DeparturesPresenter(departures);

    }

//departures
    //core
    @Override
    public void departures(Station at, Direction direction, final DepartureQueryListener result) {
        departures.departures(at, direction, result);
    }

    public void addDepartureQueryListener(DepartureQueryListener departureQueryListener) {
        departures.addDepartureQueryListener(departureQueryListener);
    }

    @Override
    public DepartureQuery lastQuery() {
        return departures.lastQuery();
    }

//tracking
    //core
    @Override
    public void addTrackedServiceListener(TrackedServiceListener trackedServiceListener) {
        tracking.addTrackedServiceListener(trackedServiceListener);
    }

    @Override
    public void watchService(String serviceId) {
        tracking.watchService(serviceId);
    }

    @Override
    public boolean isTracking() {
        return tracking.isTracking();
    }

    @Override
    public void unwatchService() {
        tracking.unwatchService();
    }

}
