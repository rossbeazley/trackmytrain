package uk.co.rossbeazley.trackmytrain.android.mobile;

import java.util.Objects;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class TrackedService extends Postman.BroadcastMessage {
    private final TrainViewModel trainViewModel;

    public TrackedService(TrainViewModel trainViewModel) {
        super("/TRACKED/SERVICE");
        this.trainViewModel = trainViewModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrackedService that = (TrackedService) o;
        return Objects.equals(trainViewModel, that.trainViewModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trainViewModel);
    }

    @Override
    public String toString() {
        return super.toString() + " {" +
                "trainViewModel=" + trainViewModel +
                '}';
    }
}
