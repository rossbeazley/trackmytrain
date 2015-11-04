package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

public interface CanQueryDepartures {
    void departures(Station at, Direction direction, DepartureQueryListener result);

    DepartureQuery lastQuery();

    interface DepartureQueryListener {
        void success(List<Train> expectedList);

        void error(TMTError tmtError);

        void loading();
    }
}
