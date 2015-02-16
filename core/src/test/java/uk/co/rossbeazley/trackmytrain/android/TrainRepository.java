package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

public class TrainRepository {
    private NetworkClient networkClient;

    TrainRepository(NetworkClient networkClient) {
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
