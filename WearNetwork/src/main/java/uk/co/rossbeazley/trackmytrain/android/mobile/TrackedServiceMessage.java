package uk.co.rossbeazley.trackmytrain.android.mobile;

import com.google.android.gms.wearable.MessageEvent;

import java.util.Objects;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.PostmanMessageFactory;

public class TrackedServiceMessage extends Postman.BroadcastMessage {
    public static final String MESSAGE_PATH = "/TRACKED/SERVICE";
    private final TrainViewModel trainViewModel;

    public TrackedServiceMessage(TrainViewModel trainViewModel) {
        super(MESSAGE_PATH + "/" + trainViewModel.id() + "/" + trainViewModel.estimatedTime() + "/" + trainViewModel.scheduledTime() + "/" + trainViewModel.platform().replace("Platform ", ""));
        this.trainViewModel = trainViewModel;
    }

    public TrainViewModel trainViewModel() {
        return trainViewModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrackedServiceMessage that = (TrackedServiceMessage) o;
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

    public static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            //messageEvent.getPath()
            // remove start path
            // split into pairs
            // deserialize
            /**
             * String id, String estimatedTime, String scheduledTime, String platform
             */
            String path = messageEvent.getPath().replace(MESSAGE_PATH, "");
            String[] parts = path.split("\\/");
            final String id = parts[1];
            final String estimatedTime = parts[2];
            final String scheduledTime = parts[3];
            final String platform = parts[4];
            TrainViewModel tvm = new TrainViewModel(id, estimatedTime, scheduledTime, platform);
            return new TrackedServiceMessage(tvm);
        }
    }
}
