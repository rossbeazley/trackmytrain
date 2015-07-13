package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class AnalyticsEventMessage extends Postman.Message {
    private static final String MESSAGE_PATH = "/ANALYTICS/EVENT";

    protected AnalyticsEventMessage() {
        super(MESSAGE_PATH);
    }

}
