package uk.co.rossbeazley.trackmytrain.android.trainRepo;

public class ServiceDetailsRequest implements NetworkClient.Request {
    private final String serviceId;

    public ServiceDetailsRequest(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String asUrlString() {
        return "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service/"+serviceId;
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
