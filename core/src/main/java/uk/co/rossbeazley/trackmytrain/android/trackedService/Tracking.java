package uk.co.rossbeazley.trackmytrain.android.trackedService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class Tracking {
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;
    private TrackMyTrain trackMyTrain;
    private final List<ServiceView> serviceViews;
    private String trackedService;
    private NarrowScheduledExecutorService.Cancelable cancelable;

    public Tracking(TrainRepository trainRepository, NarrowScheduledExecutorService executorService, TrackMyTrain trackMyTrain) {
        this.trainRepository = trainRepository;
        this.executorService = executorService;
        this.trackMyTrain = trackMyTrain;
        this.serviceViews = new ArrayList<>(2);
        this.cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
        this.trackedService = null;
    }
//core
    public void watchService(String serviceId) {
        this.trackedService = serviceId;
        refreshTrackedService();
        startTimer();
    }


    void startTimer() {
        cancelable.cancel();
        cancelable = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshTrackedService();
            }
        }, 30, TimeUnit.SECONDS);
    }

    void unwatchService() {
        this.trackedService = null;
        cancelable.cancel();
        cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
    }

//ui
    public void watch(String serviceId) {
        watchService(serviceId);
        announceTrackingStarted();
    }

    void announceTrackingStarted() {
        for (ServiceView serviceView : serviceViews) {
            serviceView.trackingStarted();
        }
    }

    public void unwatch() {
        unwatchService();
        unpresentTrackedTrain();
    }


    void refreshTrackedService() {
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
        if (trackedService != null) {
            if (train.departed()) {
                unwatch();
            } else
                for (ServiceView serviceView : new ArrayList<ServiceView>(serviceViews)) {
                    serviceView.present(new TrainViewModel(train));
                }
        }
    }

    void unpresentTrackedTrain() {
        for (ServiceView serviceView : new ArrayList<ServiceView>(serviceViews)) {
            serviceView.hide();
        }
    }

    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
        refreshTrackedService();
    }

    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);
    }
}