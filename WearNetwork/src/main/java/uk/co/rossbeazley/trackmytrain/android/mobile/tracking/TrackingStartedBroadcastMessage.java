package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

class TrackingStartedBroadcastMessage extends Postman.BroadcastMessage {
    protected TrackingStartedBroadcastMessage(String messagePath) {
        super(messagePath);
    }

    public static Postman.BroadcastMessage createTrackingStartedBroadcastMessage() {
        return new TrackingStartedBroadcastMessage("");
    }

}
