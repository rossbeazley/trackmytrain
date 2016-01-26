package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class StartedTrackingMessage extends Postman.Message {
    public static final String MESSAGE_PATH = "/TRACKING/STARTED";

    public StartedTrackingMessage() {
        super(MESSAGE_PATH);
    }

    static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            return new StartedTrackingMessage();
        }
    }
}
