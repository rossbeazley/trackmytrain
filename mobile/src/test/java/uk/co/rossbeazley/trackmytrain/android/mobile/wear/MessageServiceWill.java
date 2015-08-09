package uk.co.rossbeazley.trackmytrain.android.mobile.wear;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.mobile.Analytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.TestTrackMyTrainApp;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.AnalyticsEventMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class MessageServiceWill {

    @Test
    public void
    generateATrackingEventFromTrackingMessage() {
        new TestTrackMyTrainApp();
        CapturingAnalytics capturingAnalytics = new CapturingAnalytics();

        MessageEnvelope envelope = new MessageEnvelope(new Postman.NodeId("anyId"), new AnalyticsEventMessage("CREATED", "APP"));
        new MessageService(TrackMyTrainApp.instance, capturingAnalytics).message(envelope);

        assertThat(capturingAnalytics.events, hasItem(new Analytics.EventTrack("CREATED", "APP")));
    }

    private static class CapturingAnalytics implements Analytics {
        public List<EventTrack> events = new ArrayList<>();

        @Override
        public void timing(long millis, String category, String variable) {

        }

        @Override
        public void event(EventTrack eventTrack) {
            events.add(eventTrack);
        }

        @Override
        public void pageView(String pageName) {

        }
    }
}