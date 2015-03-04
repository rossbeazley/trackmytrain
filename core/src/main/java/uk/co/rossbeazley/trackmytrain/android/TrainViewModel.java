package uk.co.rossbeazley.trackmytrain.android;

import java.util.ArrayList;
import java.util.List;

public class TrainViewModel extends Train {

    public TrainViewModel(Train train) {
        this(train.id, train.estimatedTime, train.scheduledTime, train.platform);
    }

    public TrainViewModel(String id, String estimatedTime, String scheduledTime, String platform) {
        super(id, estimatedTime, scheduledTime, platform);
    }

    public static List<TrainViewModel> list(List<Train> expectedList) {
        List<TrainViewModel> rtn = new ArrayList<>(expectedList.size());
        for(Train train : expectedList) {
            rtn.add(new TrainViewModel(train));
        }
        return rtn;
    }
}
