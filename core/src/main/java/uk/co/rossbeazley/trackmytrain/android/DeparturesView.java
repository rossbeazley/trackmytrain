package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

public interface DeparturesView {
    void present(List<TrainViewModel> trains);
//void present(Station at, Direction direction);
}
