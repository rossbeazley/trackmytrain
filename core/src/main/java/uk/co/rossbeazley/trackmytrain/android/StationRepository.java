package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public class StationRepository {
    final KeyValuePersistence keyValuePersistence;

    public StationRepository(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
    }

    Direction getCurrentDirection() {
        String stationCode = this.keyValuePersistence.get("direction");
        return Direction.to(Station.fromString(stationCode));
    }

    void setCurrentDirection(Direction currentDirection) {
        this.keyValuePersistence.put("direction", currentDirection.station().stationCode());
    }

    Station getCurrentAt() {
        String stationCode = this.keyValuePersistence.get("at");
        return Station.fromString(stationCode);
    }

    void setCurrentAt(Station currentAt) {
        this.keyValuePersistence.put("at", currentAt.stationCode());
    }
}