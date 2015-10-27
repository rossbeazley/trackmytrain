package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueryCommand;
import uk.co.rossbeazley.trackmytrain.android.departures.StationRepository;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.trackedService.Tracking;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrain implements CanPresentTrackedTrains, CanQueryDepartures {

    private final Tracking tracking;
    private DeparturesPresenter departures;
    private final DepartureQueryCommand departureQueryCommand;

    public TrackMyTrain(NetworkClient networkClient, NarrowScheduledExecutorService executorService, KeyValuePersistence keyValuePersistence) {
        TrainRepository trainRepository = new TrainRepository(networkClient);
        tracking = new Tracking(trainRepository, executorService, this);
        departureQueryCommand = new DepartureQueryCommand(trainRepository, new StationRepository(keyValuePersistence));
        this.departures = new DeparturesPresenter(this);
    }

//departures
    //core

    @Override
    public void departures(Station at, Direction direction, final DepartureQueryCommand.Success success) {
        departureQueryCommand.departures(at, direction, success);
    }

    @Override
    public DepartureQuery lastQuery() {
        return departureQueryCommand.lastQuery();
    }

    //ui

    public void departures(Station at, Direction direction) {
        departures.departures(at, direction);
    }

    public void departures(DepartureQuery query) {
        departures.departures(query.at(), query.direction());
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



//tracking
    //core
    public void watch(String serviceId) {
        tracking.watch(serviceId);
    }

    public void unwatch() {
        tracking.unwatch();
    }

    //ui
    @Override
    public void attach(ServiceView serviceView) {
        tracking.attach(serviceView);
    }

    @Override
    public void detach(ServiceView serviceView) {
        tracking.detach(serviceView);
    }


}
