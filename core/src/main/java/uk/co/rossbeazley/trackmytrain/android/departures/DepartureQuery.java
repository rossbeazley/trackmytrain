package uk.co.rossbeazley.trackmytrain.android.departures;

/**
* Created by beazlr02 on 13/03/2015.
*/
public class DepartureQuery {

    private final Station currentAt;
    private final Direction currentDirection;

    public DepartureQuery(Station currentAt, Direction currentDirection) {

        this.currentAt = currentAt;
        this.currentDirection = currentDirection;
    }

    public Station at() {
        return currentAt;
    }

    public Direction direction() {
        return currentDirection;
    }
}
