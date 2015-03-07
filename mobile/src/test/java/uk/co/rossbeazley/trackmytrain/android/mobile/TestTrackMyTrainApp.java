package uk.co.rossbeazley.trackmytrain.android.mobile;

import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.HashMapKeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

public class TestTrackMyTrainApp extends TrackMyTrainApp {

    public static Train trackedService;
    public static KeyValuePersistence keyValuePersistence;

    public TestTrackMyTrainApp() {
        instance = getCore();
    }

    public static TrackMyTrain getCore() {
        trackedService = new Train("2", "10:00", "09:00", "1");
        keyValuePersistence = new HashMapKeyValuePersistence();
        final TrackMyTrain trackMyTrain;

        trackMyTrain = new TMTBuilder()
                .with(new ProgrammableNetworkClient())
                .with(new StubbedNarrowScheduledExecutorService())
                .with(keyValuePersistence)
                .build();

        return trackMyTrain;
    }

    private static class ProgrammableNetworkClient implements NetworkClient {
        @Override
        public void requestString(Request request, Response response) {
            if(request instanceof ServiceDetailsRequest) {
                response.ok(TestDataBuilder.jsonForTrain(trackedService));
            } else if(request instanceof DeparturesFromToRequest) {
                response.ok(TestDataBuilder.jsonForTrains(new Train("1", "", "", ""),
                new Train("2", "", "", "")));
            }
        }
    }

    private static class StubbedNarrowScheduledExecutorService implements NarrowScheduledExecutorService {
        @Override
        public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
            return Cancelable.NULL;
        }
    }
}
