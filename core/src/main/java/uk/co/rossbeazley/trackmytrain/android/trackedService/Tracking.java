package uk.co.rossbeazley.trackmytrain.android.trackedService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class Tracking {
    private final TrainRepository trainRepository;
    private final NarrowScheduledExecutorService executorService;
    private final TrackedServicePresenter trackedServicePresenter;
    private TrackMyTrain trackMyTrain;
    private String trackedServiceId;
    private NarrowScheduledExecutorService.Cancelable cancelable;

    private List<TrackedServiceListener> trackedServiceListeners;

    public Tracking(TrainRepository trainRepository, NarrowScheduledExecutorService executorService, TrackMyTrain trackMyTrain) {
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

    private interface TrackedServiceListener {
        void trackingStarted();

        void trackedServiceUpdated(Train train);
    }

    private static class TrackedServicePresenter {
        private Tracking tracking;

        private final List<ServiceView> serviceViews;

        public TrackedServicePresenter(Tracking tracking) {
            this.tracking = tracking;

            this.serviceViews = new ArrayList<>(2);
            this.tracking.addTrackedServiceListener(new TrackedServiceListener() {
                @Override
                public void trackingStarted() {
                    for (ServiceView view : serviceViews) {
                        view.trackingStarted();
                    }

                }

                @Override
                public void trackedServiceUpdated(Train train) {
                    presentTrackedTrain(train);
                }
            });
        }

        public void watch(String serviceId) {
            tracking.watchService(serviceId);
            tracking.announceTrackingStarted();
        }




        void presentTrackedTrain(Train train) {
            if (tracking.isTracking()) {
                if (train.departed()) {
                    unpresentTrackedTrain();
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
            this.tracking.refreshTrackedService();
        }

        public void detach(ServiceView serviceView) {
            this.serviceViews.remove(serviceView);

        }

        public void unwatch() {
            this.tracking.unwatchService();
            unpresentTrackedTrain();
        }
    }

    private boolean isTracking() {
        return trackedServiceId != null;
    }


}