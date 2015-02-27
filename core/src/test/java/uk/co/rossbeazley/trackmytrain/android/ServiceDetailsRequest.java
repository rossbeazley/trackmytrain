package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;

public class ServiceDetailsRequest implements NetworkClient.Request {
    public ServiceDetailsRequest(String serviceId) {
    }

    @Override
    public String asUrlString() {
        return "";
    }
}
