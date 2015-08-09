package uk.co.rossbeazley.trackmytrain.android.mobile.wear;


import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.mobile.Analytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.GoogleAnalytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.AnalyticsEventMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;

public class MessageService {
    private final TrackMyTrain instance;
    private final Analytics analytics;

    public MessageService(TrackMyTrain instance, Analytics tracker) {
        this.instance = instance;
        this.analytics = tracker;

    }

    public void message(MessageEnvelope envelope) {

        final Postman.Message message = envelope.message();

        if (message instanceof AnalyticsEventMessage) {
            AnalyticsEventMessage analyticsMessage = ((AnalyticsEventMessage) message);

            analytics.event(new Analytics.EventTrack(analyticsMessage.category(), analyticsMessage.label()));
        }


    }
}
