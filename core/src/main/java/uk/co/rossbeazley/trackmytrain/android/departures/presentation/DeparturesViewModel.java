package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public class DeparturesViewModel {

    final List<TrainViewModel> trains;
    private TrainViewModel selectedTrain;

    public DeparturesViewModel() {
        this(new ArrayList<TrainViewModel>(0));
    }

    public DeparturesViewModel(List<TrainViewModel> trains) {
        this.trains = trains;
    }

    public static DeparturesViewModel fromListOfTrains(List<Train> expectedList) {
        DeparturesViewModel rtn = new DeparturesViewModel();
        for(Train train : expectedList) {
            rtn.add(new TrainViewModel(train));
        }
        return rtn;
    }

    public TrainViewModel get(int i) {
        return trains.get(i);
    }

    public int size() {
        return trains.size();
    }

    public void add(TrainViewModel trainViewModel) {
        trains.add(trainViewModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeparturesViewModel that = (DeparturesViewModel) o;

        if (!trains.equals(that.trains)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return trains.hashCode();
    }

    public void select(TrainViewModel train) {
        selectedTrain = train;
    }

    public TrainViewModel selectedService() {
        return selectedTrain;
    }
}
