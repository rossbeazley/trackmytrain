package uk.co.rossbeazley.trackmytrain.android.mobile.wear;


import uk.co.rossbeazley.trackmytrain.android.PresentTrackedMyTrain;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.AnalyticsEventMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;

public class MessageService {
    private final PresentTrackedMyTrain instance;
    private final Analytics analytics;

    public MessageService(PresentTrackedMyTrain instance, Analytics tracker) {
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
