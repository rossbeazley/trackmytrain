package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrain {

    private List<DeparturesView> departuresViews;
    private final TrainRepository trainRepository;

    private final List<ServiceView> serviceViews;
    private String trackedService;

    public TrackMyTrain(TrainRepository trainRepository) {

        this.trainRepository = trainRepository;
        this.serviceViews = new ArrayList<ServiceView>(2);
        this.trackedService = null;
        this.departuresViews = new ArrayList<DeparturesView>(2);
    }

    public void departures(Station at, Direction direction) {
        this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
            @Override
            public void result(List<Train> expectedList) {
                for (DeparturesView departuresView : departuresViews) {
                    departuresView.present(expectedList);
                }

            }
        });
    }

    public void watch(String serviceId) {
        this.trackedService = serviceId;
        this.trainRepository.service(serviceId, new TrainRepository.ServiceSuccess(){
            @Override
            public void result(Train train) {
                for (ServiceView serviceView : serviceViews) {
                    serviceView.present(train);
                }
            }
        });
    }

    public void unwatch() {
        for (ServiceView serviceView : serviceViews) {
            serviceView.hide();
        }

        this.trackedService = null;
    }

    public void tick() {
        if(this.trackedService!=null) watch(this.trackedService);
    }

    public void attach(DeparturesView departureView) {
        this.departuresViews.add(departureView);
    }

    public void detach(DeparturesView departuresView) {
        this.departuresViews.remove(departuresView);
    }

    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
    }
    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
    }
}
