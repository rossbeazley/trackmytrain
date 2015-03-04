package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;

public class TrainViewModel {

    private final Train train;

    public TrainViewModel(Train train) {
        this(train.id, train.estimatedTime, train.scheduledTime, train.platform);
    }

    public TrainViewModel(String id, String estimatedTime, String scheduledTime, String platform) {
        train = new Train(id, estimatedTime, scheduledTime, platform);
    }

    public static List<TrainViewModel> list(List<Train> expectedList) {
        List<TrainViewModel> rtn = new ArrayList<>(expectedList.size());
        for(Train train : expectedList) {
            rtn.add(new TrainViewModel(train));
        }
        return rtn;
    }

    public String toString() {
        return train.toString();
    }

    public boolean equals(Object o) {
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
        return train.platform;
    }

    public String estimatedTime() {
        return train.estimatedTime;
    }

    public String id() {
        return train.id;
    }
}
