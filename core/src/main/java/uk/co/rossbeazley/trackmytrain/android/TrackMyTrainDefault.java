package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrainDefault implements TrackMyTrain {

    private List<DeparturesView> departuresViews;
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;

    private final List<ServiceView> serviceViews;
    private String trackedService;
    private NarrowScheduledExecutorService.Cancelable cancelable;

    public TrackMyTrainDefault(TrainRepository trainRepository, NarrowScheduledExecutorService executorService) {
        this.trainRepository = trainRepository;
        this.executorService = executorService;
        this.serviceViews = new ArrayList<ServiceView>(2);
        this.trackedService = null;
        this.departuresViews = new ArrayList<DeparturesView>(2);
    }

    @Override
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

    @Override
    public void watch(String serviceId) {
        this.trackedService = serviceId;
        tick();
        cancelable = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tick();
            }
        },30, TimeUnit.SECONDS);
    }

    @Override
    public void unwatch() {
        cancelable.cancel();
        cancelable=null;
        for (ServiceView serviceView : serviceViews) {
            serviceView.hide();
        }
        this.trackedService = null;
    }


    void tick() {
        if(this.trackedService!=null) {
            this.trainRepository.service(this.trackedService, new TrainRepository.ServiceSuccess() {
                @Override
                public void result(Train train) {
                    for (ServiceView serviceView : serviceViews) {
                        serviceView.present(train);
                    }
                }
            });
        }
    }

    @Override
    public void attach(DeparturesView departureView) {
        this.departuresViews.add(departureView);
    }

    @Override
    public void detach(DeparturesView departuresView) {
        this.departuresViews.remove(departuresView);
    }

    @Override
    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
        tick();
    }
    @Override
    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
    }
}
