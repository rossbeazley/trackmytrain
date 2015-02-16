package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TrackMyTrainTest {

    private List<Train> trainList;

    @Test
    public void theOneWhereWeRequestDetailsOfAServiceAndTheResultsAreDisplayed() {

        DeparturesView departuresView = new DeparturesView() {
            @Override
            public void present(List<Train> trains) {
                trainList = trains;
            }
        };

        TrackMyTrain tmt;
        NetworkClient networkClient = new NetworkClient() {
            @Override public void requestString(Request request, Response response) {
                String result = "{\n" +
                        "\"error\": \"\",\n" +
                        "\"trains\": [\n" +
                        "{\n" +
                        "\"id\": \"aN5S6pak5nKFawy0sXb65Q==\",\n" +
                        "\"scheduledTime\": \"21:39\",\n" +
                        "\"estimatedTime\": \"On time\",\n" +
                        "\"platform\": \"2\"\n" +
                        "},\n" +
                        "{\n" +
                        "\"id\": \"EAG/q7qfInIUZyPhCdwQKw==\",\n" +
                        "\"scheduledTime\": \"22:38\",\n" +
                        "\"estimatedTime\": \"On time\",\n" +
                        "\"platform\": \"2\"\n" +
                        "}\n" +
                        "]\n" +
                        "}";

                DeparturesFromToRequest req = new DeparturesFromToRequest(Station.fromString("SLD"), Station.fromString("CRL"));
                if(request.equals(req)) {
                    response.ok(result);
                }
            }
        };

        Train train1 = new Train("aN5S6pak5nKFawy0sXb65Q==","On time","21:39","2");
        Train train2 = new Train("EAG/q7qfInIUZyPhCdwQKw==","On time","22:38","2");
        List<Train> expectedList = Arrays.asList(train1,train2);

        tmt = new TMTBuilder()
                .with(departuresView)
                .with(networkClient)
                .build();

        Station at = Station.fromString("SLD");
        Direction direction = Direction.to(Station.fromString("CRL"));

        tmt.departures(at, direction);

        assertThat(trainList, is(expectedList));
    }

    @Test
    public void departuresToFromRequestRendersToString() {

        DeparturesFromToRequest req = new DeparturesFromToRequest(Station.fromString("MCO"),Station.fromString("SLD"));
        assertThat(req.asUrlString(),is("http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/departures/MCO/to/SLD"));
    }

}