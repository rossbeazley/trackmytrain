package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.StringNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepository;

public class TMTBuilder {

    private DeparturesView departuresView;
    private TrainRepository trainRepository;
    private NetworkClient networkClient;
    private ServiceView serviceView;

    public TMTBuilder() {
        networkClient = new StringNetworkClient();
    }

    public TMTBuilder with(DeparturesView departuresView) {
        this.departuresView = departuresView;
        return this;
    }

    public TrackMyTrain build() {
        trainRepository = new TrainRepository(networkClient);
        return new TrackMyTrain(departuresView, trainRepository);
    }

    public TMTBuilder with(NetworkClient networkClient) {
        this.networkClient = networkClient;
        return this;
    }

    public TMTBuilder with(ServiceView serviceView) {
        this.serviceView = serviceView;
        return null;
    }
}
