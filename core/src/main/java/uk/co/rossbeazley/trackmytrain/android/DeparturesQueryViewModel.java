package uk.co.rossbeazley.trackmytrain.android;

public class DeparturesQueryViewModel {
    private final Station at;
    private final Direction direction;

    public DeparturesQueryViewModel(Station at, Direction direction) {
        this.at = at;
        this.direction = direction;
    }

    public Station getAt() {
        return at;
    }

    public Direction getDirection() {
        return direction;
    }
}
