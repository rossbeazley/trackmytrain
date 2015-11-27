package uk.co.rossbeazley.trackmytrain.android.departures;

public class Station {
    private final String stationCode;
    private final String stationName;

    public Station(String stationCode) {
        this("",stationCode);
    }

    public Station(String stationName, String stationCode) {
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return stationName + " [ " + stationCode + " ]";
    }

    @Override
    public boolean equals(Object obj) {
        final Station that = (Station) obj;
        return stationCode.equals(that.stationCode) && stationName.equals(that.stationName);
    }

    public static Station fromString(String sld) {
        return Stations.fromStationCode(sld);
    }

    public String stationCode() {
        return stationCode;
    }

    public String stationName() {
        return stationName;
    }
}
