package uk.co.rossbeazley.trackmytrain.android.departures;

import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public class StationRepository {
    final KeyValuePersistence keyValuePersistence;

    public StationRepository(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
    }

    public Direction loadCurrentDirection() {
        String stationCode = this.keyValuePersistence.get("direction");
        return Direction.to(Station.fromString(stationCode));
    }

    public void loadCurrentDirection(Direction currentDirection) {
        this.keyValuePersistence.put("direction", currentDirection.station().stationCode());
    }

    public Station loadCurrentAt() {
        String stationCode = this.keyValuePersistence.get("at");
        return Station.fromString(stationCode);
    }

    public void storeCurrentAt(Station currentAt) {
        this.keyValuePersistence.put("at", currentAt.stationCode());
    }
}