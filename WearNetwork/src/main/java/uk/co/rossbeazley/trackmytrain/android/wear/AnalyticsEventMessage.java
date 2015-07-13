package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class AnalyticsEventMessage extends Postman.Message {
    public static final String MESSAGE_PATH = "/ANALYTICS/EVENT";

    public AnalyticsEventMessage() {
        super(MESSAGE_PATH);
    }


    public static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            return new AnalyticsEventMessage(); //can add category and label stuff here
        }
    }
}
