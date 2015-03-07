package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TrackMyTrainDefault implements TrackMyTrain {

    private final ArrayList<DeparturesQueryView> departuresQueryViews;
    private final KeyValuePersistence keyValuePersistence;
    private List<DeparturesView> departuresViews;
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;

    private final List<ServiceView> serviceViews;
    private String trackedService;
    private NarrowScheduledExecutorService.Cancelable cancelable;


    public TrackMyTrainDefault(NetworkClient networkClient, NarrowScheduledExecutorService executorService, KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
        this.trainRepository = new TrainRepository(networkClient);
        this.executorService = executorService;
        this.serviceViews = new ArrayList<>(2);
        this.trackedService = null;
        this.departuresViews = new ArrayList<>(2);
        cancelable= NarrowScheduledExecutorService.Cancelable.NULL;
        this.departuresQueryViews = new ArrayList<>();

    }

    @Override
    public void departures(Station at, Direction direction) {

        this.setCurrentAt(at);
        this.setCurrentDirection(direction);
        this.trainRepository.departures(at,direction, new TrainRepository.DeparturesSuccess() {
            @Override
            public void result(List<Train> expectedList) {
                presentDepartures(expectedList);
            }
        });
    }

    private void presentDepartures(List<Train> expectedList) {
        for (DeparturesView departuresView : departuresViews) {
            departuresView.present(TrainViewModel.list(expectedList));
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

    @Override
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

    @Override
    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
        tick();
    }

    @Override
    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
    }





    @Override
    public void attach(DeparturesQueryView departuresQueryView) {
        departuresQueryView.present(this.getCurrentAt(), this.getCurrentDirection());
        this.departuresQueryViews.add(departuresQueryView);
    }

    @Override
    public void detach(DeparturesQueryView departuresQueryView) {
        this.departuresQueryViews.remove(departuresQueryView);
    }


    private Direction getCurrentDirection() {
        String stationCode = this.keyValuePersistence.get("direction");
        return Direction.to(Station.fromString(stationCode));
    }

    private void setCurrentDirection(Direction currentDirection) {
        this.keyValuePersistence.put("direction",currentDirection.station().toString());
    }

    private Station getCurrentAt() {
        String stationCode = this.keyValuePersistence.get("at");
        return Station.fromString(stationCode);
    }

    private void setCurrentAt(Station currentAt) {
        this.keyValuePersistence.put("at",currentAt.toString());
    }
}
