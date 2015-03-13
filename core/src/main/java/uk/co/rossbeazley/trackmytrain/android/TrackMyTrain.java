package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesQueryViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrain {
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;

    private final List<ServiceView> serviceViews;
    private String trackedService;
    private NarrowScheduledExecutorService.Cancelable cancelable;

    private Departures departures;

    public TrackMyTrain(NetworkClient networkClient, NarrowScheduledExecutorService executorService, KeyValuePersistence keyValuePersistence) {

        this.trainRepository = new TrainRepository(networkClient);
        this.executorService = executorService;
        this.serviceViews = new ArrayList<>(2);
        this.trackedService = null;
        cancelable= NarrowScheduledExecutorService.Cancelable.NULL;
        this.departures = new Departures(keyValuePersistence, trainRepository);
    }

    public void departures(Station at, Direction direction) {
        departures.departures(at, direction);
    }


    public void attach(DeparturesView departureView) {
        departures.attach(departureView);
    }

    public void detach(DeparturesView departuresView) {
        departures.detach(departuresView);
    }








    public void watch(String serviceId) {
        this.trackedService = serviceId;
        tick();
        startTimer();
    }

    private void startTimer() {
        cancelable = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tick();
            }
        },30, TimeUnit.SECONDS);
    }

    public void unwatch() {
        cancelTracking();
        unpresentTrackedTrain();
    }

    private void cancelTracking() {
        cancelable.cancel();
        cancelable= NarrowScheduledExecutorService.Cancelable.NULL;
        this.trackedService = null;
    }


    void tick() {
        if(this.trackedService!=null) {
            this.trainRepository.service(this.trackedService, new TrainRepository.ServiceSuccess() {
                @Override
                public void result(Train train) {
                    presentTrackedTrain(train);
                }
            });
        }
    }

    private void presentTrackedTrain(Train train) {
        for (ServiceView serviceView : new ArrayList<ServiceView>(serviceViews)) {
            serviceView.present(new TrainViewModel(train));
        }
    }

    private void unpresentTrackedTrain() {
        for (ServiceView serviceView : serviceViews) {
            serviceView.hide();
        }
    }

    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
        tick();
    }

    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
    }





    public void attach(DeparturesQueryView departuresQueryView) {
        departures.attach(departuresQueryView);
    }

    public void detach(DeparturesQueryView departuresQueryView) {
        departures.detach(departuresQueryView);
    }

}
