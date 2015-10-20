package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQueryCommand;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public interface CanQueryDepartures {
    void departures(Station at, Direction direction, DepartureQueryCommand.Success success);

    DepartureQuery lastQuery();

    interface Success {
        void success(List<Train> expectedList);

        void error(TMTError tmtError);

        void loading();
    }
}
