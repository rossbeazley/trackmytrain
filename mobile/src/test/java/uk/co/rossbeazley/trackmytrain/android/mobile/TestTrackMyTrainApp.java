package uk.co.rossbeazley.trackmytrain.android.mobile;

import java.util.Arrays;

import uk.co.rossbeazley.trackmytrain.android.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class TestTrackMyTrainApp extends TrackMyTrainApp {
    public TestTrackMyTrainApp() {
        super(buildCore());
    }

    private static TrackMyTrain buildCore() {
        return new TrackMyTrain() {
            private DeparturesView departureView;

            @Override
            public void departures(Station at, Direction direction) {
                final Train train = new Train("1", "", "", "");
                final Train train1 = new Train("2", "", "", "");
                this.departureView.present(Arrays.asList(train, train1));
            }

            @Override
            public void watch(String serviceId) {

            }

            @Override
            public void unwatch() {

            }

            @Override
            public void tick() {

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

            }

            @Override
            public void detach(ServiceView serviceView) {

            }
        };
    }
}
