package fakes;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;

public class SlowRequestMapNetworkClient implements NetworkClient {

    private final Map<Request, String> mapOfRequestToString;
    private final boolean autoComplete;
    private Request request;
    private Response response;

    public SlowRequestMapNetworkClient() {
        this(new HashMap<Request, String>(), false);
    }


    public SlowRequestMapNetworkClient(Map<Request, String> hashMap) {
        this(hashMap, false);
    }

    public SlowRequestMapNetworkClient(boolean autoComplete) {
        this(new HashMap<Request, String>(), autoComplete);
    }

    public SlowRequestMapNetworkClient(Map<Request, String> hashMap, boolean autoComplete) {
        this.mapOfRequestToString = hashMap;
        this.autoComplete = autoComplete;
    }

    @Override
    public void get(Request request, Response response) {
        this.request = request;
        this.response = response;

        if(autoComplete) {
            completeRequest();
        }

    }

    public void completeRequest() {
        if (thisClientRespondsToAnyRequest()) {
            response.ok(TestDataBuilder.anyTrainsJson());
        } else if (thisClientRespondsToThisRequest(request)) {
            response.ok(mapOfRequestToString.get(request));
        }
    }
    public void completeRequestWith(String response) {
        this.response.ok(response);
    }

    private boolean thisClientRespondsToThisRequest(Request request) {
        return mapOfRequestToString.containsKey(request);
    }

    private boolean thisClientRespondsToAnyRequest() {
        return mapOfRequestToString.isEmpty();
    }

    public void errorRequest() {
        response.error("from test");
    }
}
