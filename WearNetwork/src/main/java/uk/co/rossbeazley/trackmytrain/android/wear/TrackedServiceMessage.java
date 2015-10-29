package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import java.util.Objects;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class TrackedServiceMessage extends Postman.BroadcastMessage {
    public static final String MESSAGE_PATH = "/TRACKED/SERVICE";
    private final Train train;


    public TrackedServiceMessage(Train train) {
        super(MESSAGE_PATH + "/" + encoded(train.id) + "/" + train.estimatedTime + "/" + train.scheduledTime + "/" + train.platform);
        this.train = train;
    }

    private static String encoded(String id) {
        return id.replaceAll("/","%2F");
    }

    private static String dencoded(String id) {
        return id.replaceAll("%2F","/");
    }

    public TrainViewModel trainViewModel() {
        return new TrainViewModel(train);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrackedServiceMessage that = (TrackedServiceMessage) o;
        return Objects.equals(train, that.train);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), train);
    }

    @Override
    public String toString() {
        return super.toString() + " {" +
                "train=" + train +
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
            final String id = dencoded(parts[1]);
            final String estimatedTime = parts[2];
            final String scheduledTime = parts[3];
            final String platform = parts[4];
            Train tvm = new Train(id, estimatedTime, scheduledTime, platform,false);
            return new TrackedServiceMessage(tvm);
        }
    }
}
