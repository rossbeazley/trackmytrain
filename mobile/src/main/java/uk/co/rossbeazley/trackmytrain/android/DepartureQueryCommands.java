package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;

public interface DepartureQueryCommands {

    void departures(DepartureQuery query);
}
