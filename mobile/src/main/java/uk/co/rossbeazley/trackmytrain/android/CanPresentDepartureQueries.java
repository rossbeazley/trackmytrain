package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;

public interface CanPresentDepartureQueries {


    void attach(DeparturesView departureView);

    void detach(DeparturesView departuresView);

    void attach(DeparturesQueryView departuresQueryView);

    void detach(DeparturesQueryView departuresQueryView);
}
