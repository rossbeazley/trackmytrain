package uk.co.rossbeazley.trackmytrain.android.mobile.wear;


import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.mobile.Analytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.GoogleAnalytics;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;

public class MessageService {
    private final TrackMyTrain instance;
    private final Analytics analytics;

    public MessageService(TrackMyTrain instance, Analytics tracker) {
        this.instance = instance;
        this.analytics = tracker;

    }

    public void message(MessageEnvelope message) {
        analytics.event(new Analytics.EventTrack("WearApp", "constructed"));

    }
}
