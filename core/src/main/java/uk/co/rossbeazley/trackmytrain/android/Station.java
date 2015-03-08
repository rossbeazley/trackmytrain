package uk.co.rossbeazley.trackmytrain.android;

public class Station {
    private final String stationCode;
    private final String stationName;

    public Station(String stationCode) {
        this(stationCode,"");
    }

    public Station(String stationName, String stationCode) {
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return stationCode;
    }

    @Override
    public boolean equals(Object obj) {
        return stationCode.equals(((Station) obj).stationCode);
    }

    public static Station fromString(String sld) {
        return new Station(sld);
    }
}
