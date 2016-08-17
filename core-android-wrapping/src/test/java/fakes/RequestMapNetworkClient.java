package fakes;

import java.util.Map;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;

/**
* Created by beazlr02 on 17/02/2015.
*/
public class RequestMapNetworkClient implements NetworkClient {

    public final Map<Request, String> mapOfRequestToString;

    public RequestMapNetworkClient(Map<Request, String> mapOfRequestToString) {
        this.mapOfRequestToString = mapOfRequestToString;
    }

    @Override public void get(Request request, Response response) {
        if(mapOfRequestToString.containsKey(request)) {
            response.ok(mapOfRequestToString.get(request));
        }
    }
}
