package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class StoppedTrackingMessage extends Postman.Message {

    public static final String MESSAGE_PATH = "/TRACKING/STOPPED";

    public StoppedTrackingMessage() {
        super(MESSAGE_PATH);
    }

    static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            return new StoppedTrackingMessage();
        }
    }
}
