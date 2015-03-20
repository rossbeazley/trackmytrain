package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public class DeparturesViewModel {

    final List<TrainViewModel> trains;

    public DeparturesViewModel(List<TrainViewModel> trains) {
        this.trains = trains;
    }

    public TrainViewModel get(int i) {
        return trains.get(i);
    }

    public int size() {
        return trains.size();
    }
}
