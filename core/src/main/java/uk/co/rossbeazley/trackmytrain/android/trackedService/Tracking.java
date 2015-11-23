package uk.co.rossbeazley.trackmytrain.android.trackedService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.CanTrackService;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class Tracking implements CanTrackService{
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;
    private String trackedServiceId;
    private NarrowScheduledExecutorService.Cancelable cancelable;

    private List<CanTrackService.TrackedServiceListener> trackedServiceListeners;

    public Tracking(TrainRepository trainRepository, NarrowScheduledExecutorService executorService) {
        this.trainRepository = trainRepository;
        this.executorService = executorService;
        this.cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
        this.trackedServiceId = null;
        trackedServiceListeners = new CopyOnWriteArrayList<>();
    }

    public void watchService(String serviceId) {
        this.trackedServiceId = serviceId;
        announceTrackingStarted();
        startTimer();
    }


    public void unwatchService() {
        this.trackedServiceId = null;
        cancelable.cancel();
        cancelable = NarrowScheduledExecutorService.Cancelable.NULL;
        for (CanTrackService.TrackedServiceListener listener : trackedServiceListeners) {
            listener.trackingStopped();
        }

    }

    private void startTimer() {
        refreshTrackedService();
        cancelable.cancel();
        cancelable = executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshTrackedService();
            }
        }, 30, TimeUnit.SECONDS);

    }

    private void refreshTrackedService() {
        if (this.trackedServiceId != null) {
            this.trainRepository.service(this.trackedServiceId, new TrainRepository.ServiceSuccess() {
                @Override
                public void result(Train train) {
                    storeTrackedServiceDetails(train);
                }
            });
        }
    }

    private void announceTrackingStarted() {
        for (CanTrackService.TrackedServiceListener listeners : trackedServiceListeners) {
            listeners.trackingStarted();
        }
    }

    private void storeTrackedServiceDetails(Train train) {
        for (CanTrackService.TrackedServiceListener listeners : trackedServiceListeners) {
            listeners.trackedServiceUpdated(train);

        }
        if(train.departed()) {
            unwatchService();
        }
    }

    public void addTrackedServiceListener(CanTrackService.TrackedServiceListener trackedServiceListener) {
        this.trackedServiceListeners.add(trackedServiceListener);
        if(isTracking()) {
            trackedServiceListener.trackingStarted();
            refreshTrackedService();
        }
    }

    public void removeTrackedServiceListener(CanTrackService.TrackedServiceListener trackedServiceListener) {
        this.trackedServiceListeners.remove(trackedServiceListener);
    }

    public boolean isTracking() {
        return trackedServiceId != null;
    }


}