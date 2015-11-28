package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import uk.co.rossbeazley.trackmytrain.android.DepartureQueryCommands;

/**
 * Created by beazlr02 on 05/03/2015.
 */
public interface DeparturesQueryView {
    void attach(DepartureQueryCommands departuresPresenter);

    void present(DeparturesQueryViewModel departuresQueryViewModel);

    void detach(DepartureQueryCommands departuresPresenter);
}
