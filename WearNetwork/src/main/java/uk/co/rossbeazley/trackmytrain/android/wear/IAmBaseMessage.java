package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

class IAmBaseMessage extends Postman.BroadcastMessage {
    public static final String MESSAGE_PATH = "/I/AM/BASE";

    public IAmBaseMessage() {
        super(MESSAGE_PATH);
    }

    @Override
    public String toString() {
        return super.toString() + "IAmBaseMessage{}";
    }

    static class Factory implements PostmanMessageFactory.MessageFactory {
        @Override
        public Postman.Message create(MessageEvent messageEvent) {
            return new IAmBaseMessage();
        }
    }
}
