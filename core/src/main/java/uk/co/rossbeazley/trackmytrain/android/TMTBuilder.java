package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.time.NarrowScheduledExecutorService;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.StringNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TMTBuilder {

    private TrainRepository trainRepository;
    private NetworkClient networkClient;
    private NarrowScheduledExecutorService executorService;

    public TMTBuilder() {
        networkClient = new StringNetworkClient();
    }

    public TrackMyTrain build() {
        trainRepository = new TrainRepository(networkClient);
        return new TrackMyTrainDefault(trainRepository);
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
