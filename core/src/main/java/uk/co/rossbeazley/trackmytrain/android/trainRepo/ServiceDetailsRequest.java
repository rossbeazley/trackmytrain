package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ServiceDetailsRequest implements NetworkClient.Request {
    private final String serviceId;

    public ServiceDetailsRequest(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String asUrlString() {
        String encode;
        try {
            encode = URLEncoder.encode(serviceId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            encode = serviceId;
        }
        return "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service/"+ encode;
    }

    public boolean equals(Object object) {
        ServiceDetailsRequest that = (ServiceDetailsRequest) object;
        return this.serviceId.equals(that.serviceId);
    }

    @Override
    public int hashCode() {
        return serviceId.hashCode();
    }
}
