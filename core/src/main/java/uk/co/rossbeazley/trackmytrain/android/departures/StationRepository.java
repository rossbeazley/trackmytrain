package uk.co.rossbeazley.trackmytrain.android.departures;

import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;

public class StationRepository {
    final KeyValuePersistence keyValuePersistence;

    public StationRepository(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
    }

    public Direction loadCurrentDirection() {
        String stationCode = this.keyValuePersistence.get("direction");
        return Direction.to(Stations.fromString(stationCode));
    }

    public void storeCurrentDirection(Direction currentDirection) {
        if (currentDirection != null && currentDirection.station() != null) {
            this.keyValuePersistence.put("direction", currentDirection.station().stationCode());
        }
    }

    public Station loadCurrentAt() {
        String stationCode = this.keyValuePersistence.get("at");
        return Stations.fromString(stationCode);
    }

    public void storeCurrentAt(Station currentAt) {
        if (currentAt!=null) {
            this.keyValuePersistence.put("at", currentAt.stationCode());
        }
    }

    public DepartureQuery lastDepartureQuery() {
        final Station currentAt = loadCurrentAt();
        final Direction currentDirection = loadCurrentDirection();
        return new DepartureQuery(currentAt, currentDirection);
    }
}