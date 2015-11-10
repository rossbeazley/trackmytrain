package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import uk.co.rossbeazley.trackmytrain.android.TMTError;

public interface DeparturesView {
    void present(DeparturesViewModel trains);

    void loading();

    void error(TMTError error);
}
