package uk.co.rossbeazley.trackmytrain.android.departures;

import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

/**
 * Created by rdlb on 19/10/15.
 */
public class DeparturesFacade {

    private final DeparturesPresenter departures;

    public DeparturesFacade(KeyValuePersistence keyValuePersistence, TrainRepository trainRepository) {
      StationRepository stationRepository = new StationRepository(keyValuePersistence);
      DepartureQueryCommand departureQueryCommand = new DepartureQueryCommand(trainRepository, stationRepository);
      this.departures = new DeparturesPresenter(departureQueryCommand);
  }

    public void departures(Station at, Direction direction) {
        this.departures.departures(at,direction);
    }

    public void attach(DeparturesView departureView) {
        this.departures.attach(departureView);
    }

    public void detach(DeparturesView departuresView) {
        this.departures.detach(departuresView);
    }

    public void attach(DeparturesQueryView departuresQueryView) {
        this.departures.attach(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        this.departures.detach(departuresQueryView);
    }
}
