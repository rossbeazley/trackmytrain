package uk.co.rossbeazley.trackmytrain.android.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrainDefault;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

public class TestTrackMyTrainApp extends TrackMyTrainApp {

    public static TrackMyTrain fakeTrackMyTrain;
    public static Train trackedService;

    public TestTrackMyTrainApp() {
        super(buildCore());
    }

    private static TrackMyTrain buildCore() {
        fakeTrackMyTrain = new TrackMyTrainDefault(new NetworkClient() {
            @Override
            public void requestString(Request request, Response response) {
                if(request instanceof ServiceDetailsRequest) {
                    trackedService = new Train("2", "10:00", "09:00", "1");
                    response.ok(TestDataBuilder.jsonForTrain(trackedService));
                } else if(request instanceof DeparturesFromToRequest) {
                    response.ok(TestDataBuilder.jsonForTrains(new Train("1", "", "", ""),
                    new Train("2", "", "", "")));
                }
            }
        },new NarrowScheduledExecutorService() {
            @Override
            public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
                return Cancelable.NULL;
            }
        });
        return fakeTrackMyTrain;
    }
}
