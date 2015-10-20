package fakes;

import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;

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
        if(mapOfRequestToString.containsKey(request)) {
            response.ok(mapOfRequestToString.get(request));
        }
    }
}
