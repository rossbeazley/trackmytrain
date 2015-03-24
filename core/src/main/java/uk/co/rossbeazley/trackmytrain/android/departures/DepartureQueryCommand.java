package uk.co.rossbeazley.trackmytrain.android.departures;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

/**
 * Created by beazlr02 on 13/03/2015.
 */
public class DepartureQueryCommand {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    public DepartureQueryCommand(TrainRepository trainRepository, StationRepository stationRepository) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
    }

    public void invoke(Station at, Direction direction, final DeparturesPresenter.Success success) {
        stationRepository.storeCurrentAt(at);
        stationRepository.storeCurrentDirection(direction);
        if (direction.station() != null && at != null) {
            this.trainRepository.departures(at, direction, new TrainRepository.DeparturesSuccess() {
                @Override
                public void result(List<Train> expectedList) {
                    success.success(expectedList);
                }
            });
        }
    }

    public DepartureQuery lastQuery() {
        return new DepartureQuery(stationRepository.loadCurrentAt(), stationRepository.loadCurrentDirection());
    }

}
