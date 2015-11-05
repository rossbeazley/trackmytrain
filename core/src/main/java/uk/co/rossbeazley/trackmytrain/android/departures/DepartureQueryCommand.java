package uk.co.rossbeazley.trackmytrain.android.departures;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class DepartureQueryCommand implements CanQueryDepartures {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private Collection<DepartureQueryListener> departureQueryListeners;


    public DepartureQueryCommand(TrainRepository trainRepository, StationRepository stationRepository) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.departureQueryListeners = new CopyOnWriteArrayList<>();
    }

    public void departures(Station at, Direction direction, final DepartureQueryListener result) {

        for (DepartureQueryListener listener : departureQueryListeners) {
            listener.loading();
        }


        result.loading();
        stationRepository.storeCurrentAt(at);
        stationRepository.storeCurrentDirection(direction);
        if (direction.station() != null && at != null) {
            this.trainRepository.departures(at, direction, new TrainRepository.DeparturesSuccess() {
                @Override
                public void result(List<Train> expectedList) {
                    result.success(expectedList);

                    for (DepartureQueryListener listener : departureQueryListeners) {
                        listener.success(expectedList);
                    }
                }

                @Override
                public void error(TMTError tmtError) {
                    result.error(tmtError);

                    for (DepartureQueryListener listener : departureQueryListeners) {
                        listener.error(tmtError);
                    }
                }
            });
        }
    }

    public DepartureQuery lastQuery() {
        return stationRepository.lastDepartureQuery();
    }

    @Override
    public void addDepartureQueryListener(DepartureQueryListener departureQueryListener) {
        this.departureQueryListeners.add(departureQueryListener);
    }

}
