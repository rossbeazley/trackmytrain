package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesResponse;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;

public class TrainRepository {
    private NetworkClient networkClient;

    public TrainRepository(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public void departures(Station at, Direction direction, final DeparturesSuccess result) {

        NetworkClient.Request request = new DeparturesFromToRequest(at,direction.station());
        NetworkClient.Response response = new DeparturesResponse(result);
        networkClient.requestString(request, response);
    }

    public static interface DeparturesSuccess {
        void result(List<Train> expectedList);
    }
}
