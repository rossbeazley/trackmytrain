package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class TrainRepository {
    private NetworkClient networkClient;

    public TrainRepository(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    public void departures(Station at, Direction direction, final DeparturesSuccess result) {

        NetworkClient.Request request = new DeparturesFromToRequest(at, direction.station());
        NetworkClient.Response response = new DeparturesResponse(result);
        networkClient.requestString(request, response);
    }

    public void service(String serviceId, ServiceSuccess serviceSuccess) {

        NetworkClient.Request request = new ServiceDetailsRequest(serviceId);
        NetworkClient.Response response = new ServiceResponse(serviceSuccess);
        networkClient.requestString(request, response);

    }

    public static interface DeparturesSuccess {
        void result(List<Train> expectedList);
    }

    public interface ServiceSuccess {
        void result(Train train);
    }

}
