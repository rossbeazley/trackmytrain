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


    public static interface NetworkClient {

        void requestString(Request request, Response response);

        public static interface Response {
            public void ok(String response);
        }

        public static interface Request {
            public String asUrlString();
        }

    }

    public static class TMTBuilder {

        private DeparturesView departuresView;
        private TrackMyTrain.TrainRepository trainRepository;
        private NetworkClient networkClient;

        public TMTBuilder() {
            trainRepository = new TrackMyTrain.TrainRepository() {
                @Override
                public void departures(Station at, Direction direction, final DeparturesSuccess result) {

                    NetworkClient.Request request = new DeparturesFromToRequest(at,direction.station());
                    NetworkClient.Response response = new DeparturesResponse(result);
                    networkClient.requestString(request, response);
                }
            };
        }

        public TMTBuilder with(DeparturesView departuresView) {
            this.departuresView = departuresView;
            return this;
        }

        public TrackMyTrain build() {
            return new TrackMyTrain(departuresView, trainRepository);
        }

        public TMTBuilder with(NetworkClient networkClient) {
            this.networkClient = networkClient;
            return this;
        }


    }

    public static class DeparturesFromToRequest implements NetworkClient.Request {

        public static final String WS_URL_ROOT = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/";
        private final String from;
        private final String to;

        public DeparturesFromToRequest(Station from, Station to) {
            this.from = from.toString();
            this.to = to.toString();
        }

        @Override
        public String asUrlString() {
            return WS_URL_ROOT + "departures/" + from + "/to/" + to;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            DeparturesFromToRequest that = (DeparturesFromToRequest) o;
            return from.equals(that.from) && to.equals(that.to);
        }

        @Override
        public int hashCode() {
            int result = from.hashCode();
            result = 31 * result + to.hashCode();
            return result;
        }
    }

    public static class DeparturesResponse implements NetworkClient.Response {
        private final TrackMyTrain.TrainRepository.DeparturesSuccess result;

        public DeparturesResponse(TrackMyTrain.TrainRepository.DeparturesSuccess result) {
            this.result = result;
        }

        @Override
        public void ok(String response) {
            List<Train> expectedList = parse(response);
            result.result(expectedList);
        }

        private List<Train> parse(String response) {
            return TrainRepo.createTrainList(response);
        }
    }
}