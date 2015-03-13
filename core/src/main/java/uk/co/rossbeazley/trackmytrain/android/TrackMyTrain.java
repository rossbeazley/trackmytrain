package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueryCommand;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrain {

    private final Tracking tracking;
    private DeparturesPresenter departures;

    public TrackMyTrain(NetworkClient networkClient, NarrowScheduledExecutorService executorService, KeyValuePersistence keyValuePersistence) {

        TrainRepository trainRepository = new TrainRepository(networkClient);
        tracking = new Tracking(trainRepository, executorService);


        StationRepository stationRepository = new StationRepository(keyValuePersistence);
        DepartureQueryCommand departureQueryCommand = new DepartureQueryCommand(trainRepository, stationRepository);
        this.departures = new DeparturesPresenter(departureQueryCommand);
    }

    public void departures(Station at, Direction direction) {
        departures.departures(at, direction);
    }

    public void attach(DeparturesView departureView) {
        departures.attach(departureView);
    }

    public void detach(DeparturesView departuresView) {
        departures.detach(departuresView);
    }

    public void attach(DeparturesQueryView departuresQueryView) {
        departures.attach(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        departures.detach(departuresQueryView);
    }


    public void watch(String serviceId) {
        tracking.watch(serviceId);
    }

    public void unwatch() {
        tracking.unwatch();
    }

    public void attach(ServiceView serviceView) {
        tracking.attach(serviceView);
    }

    public void detach(ServiceView serviceView) {
        tracking.detach(serviceView);
    }

}
