package uk.co.rossbeazley.trackmytrain.android.departures;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class DepartureQueryCommand implements CanQueryDepartures {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    public DepartureQueryCommand(TrainRepository trainRepository, StationRepository stationRepository) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
    }

    public void departures(Station at, Direction direction, final Result result) {
        result.loading();
        stationRepository.storeCurrentAt(at);
        stationRepository.storeCurrentDirection(direction);
        if (direction.station() != null && at != null) {
            this.trainRepository.departures(at, direction, new TrainRepository.DeparturesSuccess() {
                @Override
                public void result(List<Train> expectedList) {
                    result.success(expectedList);
                }

                @Override
                public void error(TMTError tmtError) {
                    result.error(tmtError);
                }
            });
        }
    }

    public DepartureQuery lastQuery() {
        return stationRepository.lastDepartureQuery();
    }

}
