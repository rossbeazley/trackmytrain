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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartureQuery that = (DepartureQuery) o;

        if (currentAt != null ? !currentAt.equals(that.currentAt) : that.currentAt != null)
            return false;
        if (currentDirection != null ? !currentDirection.equals(that.currentDirection) : that.currentDirection != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentAt != null ? currentAt.hashCode() : 0;
        result = 31 * result + (currentDirection != null ? currentDirection.hashCode() : 0);
        return result;
    }
}
