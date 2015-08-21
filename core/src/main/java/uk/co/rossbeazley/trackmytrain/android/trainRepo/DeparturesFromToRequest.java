package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public class DeparturesFromToRequest implements NetworkClient.Request {

    public static final String WS_URL_ROOT = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/";
    private final String from;
    private final String to;

    public DeparturesFromToRequest(Station from, Station to) {
        this.from = from.stationCode();
        this.to = to.stationCode();
    }

    @Override
    public String toString() {
        return "Departures from " + from + " to " + to;
    }

    @Override
    public String asUrlString() {
        return WS_URL_ROOT + "departures/" + from + "/to/" + to;
    }

    @Override
    public void output(Output output) {

    }

    @Override
    public String method() {
        return NetworkClient.Request.GET;
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
