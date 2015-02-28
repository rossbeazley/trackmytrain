package uk.co.rossbeazley.trackmytrain.android.mobile;

import java.util.Arrays;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class TestTrackMyTrainApp extends TrackMyTrainApp {

    public static FakeTrackMyTrain fakeTrackMyTrain;

    public TestTrackMyTrainApp() {
        super(buildCore());
    }

    private static TrackMyTrain buildCore() {
        fakeTrackMyTrain = new FakeTrackMyTrain();
        return fakeTrackMyTrain;
    }

    public static class FakeTrackMyTrain implements TrackMyTrain {
        private DeparturesView departureView;
        private List<Train> trains = Arrays.asList(new Train("1", "", "", ""), new Train("2", "", "", ""));
        private Train watching;
        private ServiceView serviceView;


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
            this.serviceView.hide();
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
            this.serviceView = serviceView;
        }

        @Override
        public void detach(ServiceView serviceView) {
            this.serviceView = null;
        }

        public void announceWatchedService(Train expectedTrain) {
            setWatching(expectedTrain);
        }

        public void setWatching(Train watching) {
            this.watching = watching;
            this.serviceView.present(watching);
        }
    }
}
