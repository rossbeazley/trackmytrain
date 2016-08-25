package uk.co.rossbeazley.trackmytrain.android.departures;

public class Direction {

    private static final Object TO = "TO";

    private final Object to;
    private final Station station;

    private Direction(Object to, Station station) {
        this.to = to;
        this.station = station;
    }

    @Override
    public boolean equals(Object obj) {
        Direction that = (Direction) obj;
        return to.equals(that.to) && station.equals(that.station);
    }

    @Override
    public String toString() {
        return  to + " " + station;
    }

    public static Direction to(Station station) {
        return new Direction(TO, station);
    }

    public Station station() {
        return station;
    }
}
