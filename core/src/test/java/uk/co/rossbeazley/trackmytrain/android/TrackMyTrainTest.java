package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TrackMyTrainTest {

    private List<Train> trainList;

    @Test
    public void theOneWhereWeRequestDetailsOfAServiceAndTheResultsAreDisplayed() {

        final List<Train> expectedList = Arrays.asList(anyTrain(), anyTrain());

        DeparturesView departuresView = new DeparturesView() {
            @Override
            public void present(List<Train> trains) {
                trainList = trains;
            }
        };

        TrainRepository trainRepository = new TrainRepository() {
            @Override
            public void departures(Station at, Direction direction, DeparturesSuccess result) {
                if(at.equals(Station.fromString("SLD")) && direction.equals(Direction.to(Station.fromString("CRL")))) {
                    result.result(expectedList);
                }
            }
        };

        TrackMyTrain tmt = new TrackMyTrain(departuresView, trainRepository);

        Station at = Station.fromString("SLD");
        Direction direction = Direction.to(Station.fromString("CRL"));

        tmt.departures(at, direction);

        assertThat(trainList, is(expectedList));
    }

    private Train anyTrain() {
        return new Train("","","");
    }

    private interface TrainRepository {

        void departures(Station at, Direction direction, DeparturesSuccess result);

        public static interface DeparturesSuccess {
            void result(List<Train> expectedList);
        }

    }

    private interface DeparturesView {
        void present(List<Train> trains);
    }

    private class TrackMyTrain {

        private final DeparturesView departuresView;
        private final TrainRepository trainRepository;

        public TrackMyTrain(DeparturesView departuresView, TrainRepository trainRepository) {

            this.departuresView = departuresView;
            this.trainRepository = trainRepository;
        }

        public void departures(Station at, Direction direction) {
            this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
                @Override
                public void result(List<Train> expectedList) {
                    departuresView.present(expectedList);
                }
            });
        }
    }

    private static class Direction {

        private static final Object TO = new Object();

        private final Object to;
        private final Station station;

        public Direction(Object to, Station station) {
            this.to = to;
            this.station = station;
        }

        @Override
        public boolean equals(Object obj) {
            Direction that = (Direction) obj;
            return to.equals(that.to) && station.equals(that.station);
        }

        public static Direction to(Station station) {
            return new Direction(TO, station);
        }
    }

    private static class Station {
        private final String stationCode;

        public Station(String stationCode) {
            this.stationCode = stationCode;
        }

        @Override
        public boolean equals(Object obj) {
            return stationCode.equals(((Station) obj).stationCode);
        }

        public static Station fromString(String sld) {
            return new Station(sld);
        }
    }
}