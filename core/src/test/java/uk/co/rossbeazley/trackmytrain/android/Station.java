package uk.co.rossbeazley.trackmytrain.android;

public class Station {
    private final String stationCode;

    public Station(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public boolean equals(Object obj) {
        return stationCode.equals(((Station) obj).stationCode);
    }

    public static Station fromString(String sld) {
        return new Station(sld);
    }
}
