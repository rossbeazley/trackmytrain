package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrain {

    private List<DeparturesView> departuresViews;
    private final TrainRepository trainRepository;
    private final ServiceView serviceView;

    private String trackedService;

    public TrackMyTrain(TrainRepository trainRepository, ServiceView serviceView) {

        this.trainRepository = trainRepository;
        this.serviceView = serviceView;
        this.trackedService = null;
    }

    public void departures(Station at, Direction direction) {
        this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
            @Override
            public void result(List<Train> expectedList) {

                //TrackMyTrain.this.departuresView.present(expectedList);
            }
        });
    }

    public void watch(String serviceId) {
        this.trackedService = serviceId;
        this.trainRepository.service(serviceId, new TrainRepository.ServiceSuccess(){
            @Override
            public void result(Train train) {
                TrackMyTrain.this.serviceView.present(train);
            }
        });
    }

    public void unwatch() {
        this.serviceView.hide();
        this.trackedService = null;
    }

    public void tick() {
        if(this.trackedService!=null) watch(this.trackedService);
    }

    public void attach(DeparturesView departuresView) {

    }
}
