package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;

public class TrainViewModel {

    private final Train train;

    public TrainViewModel(Train train) {
        this(train.id, train.estimatedTime, train.scheduledTime, train.platform);
    }

    public TrainViewModel(String id, String estimatedTime, String scheduledTime, String platform) {
        train = new Train(id, estimatedTime, scheduledTime, platform);
    }

    public static DeparturesViewModel list(List<Train> expectedList) {
        DeparturesViewModel rtn = new DeparturesViewModel();
        for(Train train : expectedList) {
            rtn.add(new TrainViewModel(train));
        }
        return rtn;
    }

    public String toString() {
        return train.toString();
    }

    public boolean equals(Object o) {
        if (o==null) return false;
        TrainViewModel that = (TrainViewModel) o;
        return train.equals(((TrainViewModel) o).train);
    }

    public int hashCode() {
        return train.hashCode();
    }

    public String scheduledTime() {
        return train.scheduledTime;
    }

    public String platform() {
        return String.format("Platform %s", train.platform);
    }

    public String estimatedTime() {
        return train.estimatedTime;
    }

    public String id() {
        return train.id;
    }

    public boolean isLate() {
        return train.isLate();
    }
}
