package uk.co.rossbeazley.trackmytrain.android.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrainDefault;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;

public class TestTrackMyTrainApp extends TrackMyTrainApp {

    public static FakeTrackMyTrain fakeTrackMyTrain;

    public TestTrackMyTrainApp() {
        super(buildCore());
    }

    private static TrackMyTrain buildCore() {
        fakeTrackMyTrain = new FakeTrackMyTrain(null,null);
        return fakeTrackMyTrain;
    }

    public static class FakeTrackMyTrain extends TrackMyTrainDefault {
        private DeparturesView departureView;
        private List<Train> trains = Arrays.asList(new Train("1", "", "", ""), new Train("2", "", "", ""));
        private Train watching;

        private final List<ServiceView> serviceViews = new ArrayList<>();

        public FakeTrackMyTrain(NetworkClient networkClient, NarrowScheduledExecutorService executorService) {
            super(networkClient, executorService);
        }

        @Override
        public void departures(Station at, Direction direction) {
            this.departureView.present(trains);
        }

        @Override
        public void watch(String serviceId) {
            for(Train train : trains) {
                if(train.id.equals(serviceId)) {
                    this.setWatching(train);
                }
            }
        }

        @Override
        public void unwatch() {
            this.watching = null;

            for (ServiceView serviceView : serviceViews) {
                serviceView.hide();
            }
        }

        @Override
        public void attach(DeparturesView departureView) {
            this.departureView = departureView;
        }

        @Override
        public void detach(DeparturesView departuresView) {
            this.departureView = null;
        }


        @Override
        public void attach(ServiceView serviceView) {
            if(this.watching!=null) {
                serviceView.present(this.watching);
            }
            this.serviceViews.add(serviceView);
        }
        @Override
        public void detach(ServiceView serviceView) {
            this.serviceViews.remove(serviceView);
        }

        public void announceWatchedService(Train expectedTrain) {
            setWatching(expectedTrain);
        }

        public void setWatching(Train watching) {
            this.watching = watching;
            for (ServiceView serviceView : serviceViews) {
                serviceView.present(watching);
            }
        }
    }
}
