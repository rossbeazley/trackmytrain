package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.RequestMapNetworkClient;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DeparturesTest {



    @Test
    public void theOneWhereWeRequestDetailsOfAServiceAndTheResultsAreDisplayed() {

        CapturingDeparturesView departuresView = new CapturingDeparturesView();
/**
 * connascence of algorithm with all this json and url strings :S
 */
        TrackMyTrain tmt;

        final Train train1, train2;
        train1 = new Train("aN5S6pak5nKFawy0sXb65Q==", "On time", "21:39", "2");
        train2 = new Train("EAG/q7qfInIUZyPhCdwQKw==", "On time", "22:38", "2");

        NetworkClient networkClient = new RequestMapNetworkClient(new HashMap<NetworkClient.Request, String>() {{
            put(new DeparturesFromToRequest(Station.fromString("SLD"), Station.fromString("CRL")), TestDataBuilder.jsonForTrains(train1, train2));
        }


        });

        List<TrainViewModel> expectedList = TrainViewModel.list(Arrays.asList(train1,train2));

        tmt = new TMTBuilder()
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

}