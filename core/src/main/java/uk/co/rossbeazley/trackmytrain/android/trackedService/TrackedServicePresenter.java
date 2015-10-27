package uk.co.rossbeazley.trackmytrain.android.trackedService;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

class TrackedServicePresenter {
    private Tracking tracking;

    private final List<ServiceView> serviceViews;

    public TrackedServicePresenter(Tracking tracking) {
        this.tracking = tracking;

        this.serviceViews = new ArrayList<>(2);
        this.tracking.addTrackedServiceListener(new Tracking.TrackedServiceListener() {
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
