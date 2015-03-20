package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public interface DeparturesView {
    void present(List<TrainViewModel> trains);
}
