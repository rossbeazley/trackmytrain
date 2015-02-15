package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

public class TrackMyTrain {

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
                TrackMyTrain.this.departuresView.present(expectedList);
            }
        });
    }

    public static interface TrainRepository {

        void departures(Station at, Direction direction, TrainRepository.DeparturesSuccess result);

        public static interface DeparturesSuccess {
            void result(List<Train> expectedList);
        }

    }
}
