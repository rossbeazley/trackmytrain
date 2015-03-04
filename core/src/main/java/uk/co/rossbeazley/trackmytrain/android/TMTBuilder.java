package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.DefaultNarrowScheduledExecutorService;
import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.StringNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TMTBuilder {

    private NetworkClient networkClient;
    private NarrowScheduledExecutorService executorService;

    public TMTBuilder() {
        networkClient = new StringNetworkClient();
        executorService = new DefaultNarrowScheduledExecutorService();
    }

    public TrackMyTrain build() {
        return new TrackMyTrainDefault(networkClient, executorService);
    }

    public TMTBuilder with(NetworkClient networkClient) {
        this.networkClient = networkClient;
        return this;
    }

    public TMTBuilder with(NarrowScheduledExecutorService ness) {
        executorService = ness;
        return this;
    }
}
