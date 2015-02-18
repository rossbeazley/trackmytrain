package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrain {

    private final DeparturesView departuresView;
    private final TrainRepository trainRepository;
    private final ServiceView serviceView;

    public TrackMyTrain(DeparturesView departuresView, TrainRepository trainRepository, ServiceView serviceView) {

        this.departuresView = departuresView;
        this.trainRepository = trainRepository;
        this.serviceView = serviceView;
    }

    public void departures(Station at, Direction direction) {
        this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
            @Override
            public void result(List<Train> expectedList) {
                TrackMyTrain.this.departuresView.present(expectedList);
            }
        });
    }

    public void watch(String serviceId) {
        this.trainRepository.service(serviceId, new TrainRepository.ServiceSuccess(){
            @Override
            public void result(Train train) {
                TrackMyTrain.this.serviceView.present(train);
            }
        });
    }
}
