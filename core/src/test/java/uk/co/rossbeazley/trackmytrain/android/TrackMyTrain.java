package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

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

}
