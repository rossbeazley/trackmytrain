package fakes;

import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;

public class SlowRequestMapNetworkClient implements NetworkClient {

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
        if (thisClientRespondsToAnyRequest()) {
            response.ok(TestDataBuilder.anyTrainsJson());
        } else if (thisClientRespondsToThisRequest(request)) {
            response.ok(mapOfRequestToString.get(request));
        }
    }

    private boolean thisClientRespondsToThisRequest(Request request) {
        return mapOfRequestToString.containsKey(request);
    }

    private boolean thisClientRespondsToAnyRequest() {
        return mapOfRequestToString.isEmpty();
    }
}
