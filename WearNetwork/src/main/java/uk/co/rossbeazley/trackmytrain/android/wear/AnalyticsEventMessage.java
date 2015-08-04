package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class AnalyticsEventMessage extends Postman.Message {
    public static final String MESSAGE_PATH = "/ANALYTICS/EVENT";
    private String eventName;

    public AnalyticsEventMessage(String eventName) {
        super(MESSAGE_PATH);
        this.eventName = eventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AnalyticsEventMessage that = (AnalyticsEventMessage) o;

        return !(eventName != null ? !eventName.equals(that.eventName) : that.eventName != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        return result;
    }

    public static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            return new AnalyticsEventMessage(""); //can add category and label stuff here
        }
    }
}
