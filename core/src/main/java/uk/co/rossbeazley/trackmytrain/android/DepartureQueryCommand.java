package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
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
        stationRepository.setCurrentAt(at);
        stationRepository.setCurrentDirection(direction);
        this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
            @Override
            public void result(List<Train> expectedList) {
                success.success(expectedList);
            }
        });
    }

    public DepartureQuery lastQuery() {
        return new DepartureQuery(stationRepository.getCurrentAt(), stationRepository.getCurrentDirection());
    }

    public static class DepartureQuery {

        private final Station currentAt;
        private final Direction currentDirection;

        public DepartureQuery(Station currentAt, Direction currentDirection) {

            this.currentAt = currentAt;
            this.currentDirection = currentDirection;
        }

        public Station at() {
            return currentAt;
        }

        public Direction direction() {
            return currentDirection;
        }
    }
}
