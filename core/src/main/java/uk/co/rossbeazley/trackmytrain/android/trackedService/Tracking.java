package uk.co.rossbeazley.trackmytrain.android.trackedService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.PresentTrackedMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class Tracking {
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;
    private final TrackedServicePresenter trackedServicePresenter;
    private PresentTrackedMyTrain trackMyTrain;
    private String trackedServiceId;
    private NarrowScheduledExecutorService.Cancelable cancelable;

    private List<TrackedServiceListener> trackedServiceListeners;

    public Tracking(TrainRepository trainRepository, NarrowScheduledExecutorService executorService, PresentTrackedMyTrain trackMyTrain) {
        this.trainRepository = trainRepository;
        this.executorService = executorService;
        this.trackMyTrain = trackMyTrain;
        this.cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
        this.trackedServiceId = null;
        trackedServiceListeners = new CopyOnWriteArrayList<>();
        trackedServicePresenter = new TrackedServicePresenter(this);
    }
//core
    public void watchService(String serviceId) {
        this.trackedServiceId = serviceId;
        startTimer();
    }


    void startTimer() {
        refreshTrackedService();
        cancelable.cancel();
        cancelable = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshTrackedService();
            }
        }, 30, TimeUnit.SECONDS);

    }

    void unwatchService() {
        this.trackedServiceId = null;
        cancelable.cancel();
        cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
    }

    void refreshTrackedService() {
        if (this.trackedServiceId != null) {
            this.trainRepository.service(this.trackedServiceId, new TrainRepository.ServiceSuccess() {
                @Override
                public void result(Train train) {
                    storeTrackedServiceDetails(train);
                }
            });
        }
    }

//ui
    public void watch(String serviceId) {
        this.trackedServicePresenter.watch(serviceId);
    }

    void announceTrackingStarted() {
        for (TrackedServiceListener listeners : trackedServiceListeners) {
            listeners.trackingStarted();
        }
    }

    public void unwatch() {
        trackedServicePresenter.unwatch();
    }


    private void storeTrackedServiceDetails(Train train) {
        for (TrackedServiceListener listeners : trackedServiceListeners) {
            listeners.trackedServiceUpdated(train);
        }
    }


    public void attach(ServiceView serviceView) {
        this.trackedServicePresenter.attach(serviceView);
    }

    public void detach(ServiceView serviceView) {
        this.trackedServicePresenter.detach(serviceView);

    }
    public void addTrackedServiceListener(TrackedServiceListener trackedServiceListener) {
        this.trackedServiceListeners.add(trackedServiceListener);
    }

    public interface TrackedServiceListener {
        void trackingStarted();

        void trackedServiceUpdated(Train train);
    }

    public boolean isTracking() {
        return trackedServiceId != null;
    }


}