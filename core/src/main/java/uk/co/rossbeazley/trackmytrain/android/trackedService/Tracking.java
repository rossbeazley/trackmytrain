package uk.co.rossbeazley.trackmytrain.android.trackedService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class Tracking {
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;
    private final List<ServiceView> serviceViews;
    private String trackedService;
    private NarrowScheduledExecutorService.Cancelable cancelable;

    public Tracking(TrainRepository trainRepository, NarrowScheduledExecutorService executorService) {
        this.trainRepository = trainRepository;
        this.executorService = executorService;
        this.serviceViews = new ArrayList<>(2);
        this.cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
        this.trackedService = null;
    }

    public void watch(String serviceId) {
        this.trackedService = serviceId;
        tick();
        startTimer();
    }

    void startTimer() {
        cancelable.cancel();
        cancelable = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tick();
            }
        }, 30, TimeUnit.SECONDS);
    }

    public void unwatch() {
        cancelTracking();
        unpresentTrackedTrain();
    }

    void cancelTracking() {
        this.trackedService = null;
        cancelable.cancel();
        cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
    }

    void tick() {
        if (this.trackedService != null) {
            this.trainRepository.service(this.trackedService, new TrainRepository.ServiceSuccess() {
                @Override
                public void result(Train train) {
                    presentTrackedTrain(train);
                }
            });
        }
    }

    void presentTrackedTrain(Train train) {
        for (ServiceView serviceView : new ArrayList<ServiceView>(serviceViews)) {
            serviceView.present(new TrainViewModel(train));
        }
    }

    void unpresentTrackedTrain() {
        for (ServiceView serviceView : new ArrayList<ServiceView>(serviceViews)) {
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
}