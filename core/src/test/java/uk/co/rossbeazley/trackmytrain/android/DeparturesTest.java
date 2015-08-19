package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DeparturesTest {



    @Test
    public void theOneWhereWeRequestDetailsOfAServiceAndWeStartLoading() {

        CapturingDeparturesView departuresView = new CapturingDeparturesView();

        TrackMyTrain tmt;

        final Station fromStation = TestDataBuilder.anyStation();
        final Station toStation = TestDataBuilder.anyStation();
        SlowRequestMapNetworkClient networkClient = new SlowRequestMapNetworkClient(new HashMap<NetworkClient.Request, String>() {{
            put(new DeparturesFromToRequest(fromStation, toStation), TestDataBuilder.anyTrainsJson());
        }});

        tmt = TestDataBuilder.TMTBuilder()
                .with(networkClient)
                .build();

        tmt.attach(departuresView);

        Station at = fromStation;
        Direction direction = Direction.to(toStation);

        tmt.departures(at, direction);

        assertThat(departuresView.isLoading, is(true));
    }

    @Test
    public void theOneWhereWeRequestDetailsOfAServiceAndTheResultsAreDisplayed() {

        CapturingDeparturesView departuresView = new CapturingDeparturesView();
/**
 * connascence of algorithm with all this json and url strings :S
 */
        TrackMyTrain tmt;

        final Train train1, train2;
        train1 = new Train("aN5S6pak5nKFawy0sXb65Q==", "On time", "21:39", "2", false);
        train2 = new Train("EAG/q7qfInIUZyPhCdwQKw==", "On time", "22:38", "2", false);

        NetworkClient networkClient = new RequestMapNetworkClient(new HashMap<NetworkClient.Request, String>() {{
            put(new DeparturesFromToRequest(Station.fromString("SLD"), Station.fromString("CRL")), TestDataBuilder.jsonForTrains(train1, train2));
        }


        });

        DeparturesViewModel expectedList = TrainViewModel.list(Arrays.asList(train1, train2));

        tmt = TestDataBuilder.TMTBuilder()
                .with(networkClient)
                .build();

        tmt.attach(departuresView);

        Station at = Station.fromString("SLD");
        Direction direction = Direction.to(Station.fromString("CRL"));

        tmt.departures(at, direction);

        assertThat(departuresView.trainList, is(expectedList));
    }

    @Test
    public void departuresToFromRequestRendersToString() {
        DeparturesFromToRequest req = new DeparturesFromToRequest(Station.fromString("MCO"),Station.fromString("SLD"));
        assertThat(req.asUrlString(),is(DeparturesFromToRequest.WS_URL_ROOT + "departures/MCO/to/SLD"));
    }

    public static class SlowRequestMapNetworkClient implements NetworkClient {

        private final Map<Request, String> mapOfRequestToString;
        private Request request;
        private Response response;

        public SlowRequestMapNetworkClient(Map<Request, String> hashMap) {
            this.mapOfRequestToString = hashMap;
        }

        @Override
        public void get(Request request, Response response) {
            this.request = request;
            this.response = response;

        }

        public void completeRequest() {
            if(mapOfRequestToString.containsKey(request)) {
                response.ok(mapOfRequestToString.get(request));
            }
        }
    }
}