package uk.co.rossbeazley.trackmytrain.android.mobile.wear;


import uk.co.rossbeazley.trackmytrain.android.CanTrackService;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.AnalyticsEventMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;
import uk.co.rossbeazley.trackmytrain.android.wear.StopTrackingMessage;

public class MessageService {
    private final CanTrackService instance;
    private final Analytics analytics;

    public MessageService(CanTrackService instance, Analytics tracker) {
        this.instance = instance;
        this.analytics = tracker;

    }

    public void message(MessageEnvelope envelope) {

        final Postman.Message message = envelope.message();

        if (message instanceof AnalyticsEventMessage) {
            AnalyticsEventMessage analyticsMessage = ((AnalyticsEventMessage) message);

            analytics.event(new Analytics.EventTrack(analyticsMessage.category(), analyticsMessage.label()));
        } else if(message instanceof StopTrackingMessage) {
            instance.unwatchService();
        }


    }
}
