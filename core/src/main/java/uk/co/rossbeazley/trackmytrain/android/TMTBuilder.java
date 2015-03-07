package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.DefaultNarrowScheduledExecutorService;
import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.StringNetworkClient;

public class TMTBuilder {

    private NetworkClient networkClient;
    private NarrowScheduledExecutorService executorService;
    private KeyValuePersistence keyValuePersistence;

    public TMTBuilder() {
        networkClient = new StringNetworkClient();
        executorService = new DefaultNarrowScheduledExecutorService();
        keyValuePersistence = new HashMapKeyValuePersistence();
    }

    public TrackMyTrain build() {
        return new TrackMyTrain(networkClient, executorService, keyValuePersistence);
    }

    public TMTBuilder with(NetworkClient networkClient) {
        this.networkClient = networkClient;
        return this;
    }

    public TMTBuilder with(NarrowScheduledExecutorService ness) {
        executorService = ness;
        return this;
    }

    public TMTBuilder with(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
        return this;
    }
}
