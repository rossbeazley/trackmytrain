package uk.co.rossbeazley.trackmytrain.android.departures;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public interface DeparturesView {
    void present(List<TrainViewModel> trains);
//void present(Station at, Direction direction);
}