package uk.co.rossbeazley.trackmytrain.android.wear;

import android.support.annotation.NonNull;

import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public class AnalyticsEventMessage extends Postman.Message {
    public static final String MESSAGE_PATH = "/TMT/ANALYTICS/EVENT";
    private String category;
    private final String label;

    public AnalyticsEventMessage(String category, String label) {
        super(MESSAGE_PATH);
        this.category = category;
        this.label = label;
    }

    @Override
    public String messageAsString() {
        return super.messageAsString() + params();
    }

    @Override
    public String toString() {
        return super.toString() + params();
    }

    @NonNull
    private String params() {
        return "/" + category + "/" + label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AnalyticsEventMessage that = (AnalyticsEventMessage) o;

        return !(category != null ? !category.equals(that.category) : that.category != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    public String category() {
        return category;
    }

    public String label() {
        return label;
    }

    public static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            String[] parts = messageEvent.getPath().replace(MESSAGE_PATH + "/", "").split("/");
            return new AnalyticsEventMessage(parts[0], parts[1]); //can add category and label stuff here
        }
    }
}
