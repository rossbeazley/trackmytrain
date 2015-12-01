package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.Stations;

public class DeparturesQueryViewModel {
    private Station at;
    private Direction direction;

    private final List<Station> stations;

    public DeparturesQueryViewModel(Station at, Direction direction) {
        this(at, direction, new Stations().list());
    }

    public DeparturesQueryViewModel(Station at, Direction direction, List<Station> stations) {
        this.at = at;
        this.direction = direction;
        this.stations = stations;
    }

    public DeparturesQueryViewModel(DepartureQuery departureQuery) {
        this(departureQuery.at(), departureQuery.direction());
    }


    public Station getAt() {
        return at;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Station> stations() {
        return stations;
    }

    public void setAt(Station at) {
        this.at = at;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public DepartureQuery departuresQuery() {
        return new DepartureQuery(at, direction);
    }

    public void swapStations() {
        Station oldat = at;
        at = (direction != null ? direction.station() : null);
        direction = Direction.to(oldat);
    }
}
