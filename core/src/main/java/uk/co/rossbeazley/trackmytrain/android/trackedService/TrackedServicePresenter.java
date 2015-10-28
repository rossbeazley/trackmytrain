package uk.co.rossbeazley.trackmytrain.android.trackedService;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.CanTrackService;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public class TrackedServicePresenter {
    private CanTrackService tracking;

    private final List<ServiceView> serviceViews;
    private TrainViewModel lastPresentedTrainViewModel;

    public TrackedServicePresenter(CanTrackService tracking) {
        this.tracking = tracking;

        this.serviceViews = new ArrayList<>(2);
        this.tracking.addTrackedServiceListener(new CanTrackService.TrackedServiceListener() {
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

            @Override
            public void trackingStopped() {
                unpresentTrackedTrain();
            }
        });
    }

    public void watch(String serviceId) {
        tracking.watchService(serviceId);
    }




    void presentTrackedTrain(Train train) {
        if (tracking.isTracking()) {
            if (train.departed()) {
                unpresentTrackedTrain();
                lastPresentedTrainViewModel = null;
            } else{
                    lastPresentedTrainViewModel = new TrainViewModel(train);
                for (ServiceView serviceView : new ArrayList<>(serviceViews)) {
                    serviceView.present(lastPresentedTrainViewModel);

                }}

        }else {
            lastPresentedTrainViewModel=null;
        }
    }

    void unpresentTrackedTrain() {
        for (ServiceView serviceView : new ArrayList<>(serviceViews)) {
            serviceView.hide();
        }
    }

    public void attach(ServiceView serviceView) {
        this.serviceViews.add(serviceView);
        if(lastPresentedTrainViewModel!=null) {
            serviceView.present(lastPresentedTrainViewModel);
        }
    }

    public void detach(ServiceView serviceView) {
        this.serviceViews.remove(serviceView);

    }

    public void unwatch() {
        this.tracking.unwatchService();

    }
}
