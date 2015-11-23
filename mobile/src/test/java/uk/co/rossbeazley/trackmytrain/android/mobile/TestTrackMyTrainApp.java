package uk.co.rossbeazley.trackmytrain.android.mobile;

import fakes.ControllableExecutorService;
import uk.co.rossbeazley.trackmytrain.android.HashMapKeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import fakes.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;
import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

public class TestTrackMyTrainApp extends TrackMyTrainApp {

    public static Train trackedService;
    public static KeyValuePersistence keyValuePersistence;
    public static ProgrammableNetworkClient networkClient;
    public static ControllableExecutorService executorService;

    public TestTrackMyTrainApp() {
        instance = getCore();
    }

    public static TrackMyTrain getCore() {
        trackedService = TestDataBuilder.anyTrain();
        keyValuePersistence = new HashMapKeyValuePersistence(){{
            this.put("at","CRL");
            this.put("direction","SLD");

        }};
        final TrackMyTrain trackMyTrain;

        networkClient = new ProgrammableNetworkClient();
        executorService = new ControllableExecutorService();
        trackMyTrain = new TMTBuilder()
                .with(networkClient)
                .with(executorService)
                .with(keyValuePersistence)
                .build();

        return trackMyTrain;
    }

    private static class ProgrammableNetworkClient implements NetworkClient {
        @Override
        public void get(Request request, Response response) {
            if(request instanceof ServiceDetailsRequest) {
                response.ok(TestDataBuilder.jsonForTrain(trackedService));
            } else if(request instanceof DeparturesFromToRequest) {
                response.ok(TestDataBuilder.anyTrainsJson());
            }
        }
    }

}
