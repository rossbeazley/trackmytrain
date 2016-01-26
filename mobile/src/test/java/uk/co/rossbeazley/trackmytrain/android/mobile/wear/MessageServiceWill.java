package uk.co.rossbeazley.trackmytrain.android.mobile.wear;

import org.junit.Before;
import org.junit.Test;

import fakes.CapturingAnalytics;
import uk.co.rossbeazley.trackmytrain.android.TrackingAServiceTest;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;
import uk.co.rossbeazley.trackmytrain.android.mobile.TestTrackMyTrainApp;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.AnalyticsEventMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;
import uk.co.rossbeazley.trackmytrain.android.wear.StopTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.StoppedTrackingMessage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class MessageServiceWill {

    private MessageService messageService;
    private TrackingAServiceTest.CapturingCanTrackService instance;
    private CapturingAnalytics capturingAnalytics;

    @Test
    public void
    generateATrackingEventFromTrackingMessage() {

        MessageEnvelope envelope = new MessageEnvelope(new Postman.NodeId("anyId"), new AnalyticsEventMessage("CREATED", "APP"));
        messageService.message(envelope);

        assertThat(capturingAnalytics.events, hasItem(new Analytics.EventTrack("CREATED", "APP")));
    }

    @Test
    public void
    unwatchServiceFromStopTrackingMessage() {
        MessageEnvelope envelope = new MessageEnvelope(new Postman.NodeId("anyId"), new StopTrackingMessage());
        messageService.message(envelope);

        assertThat(instance.isTracking(), is(false));

    }

    @Before
    public void setUp() {
        instance = new TrackingAServiceTest.CapturingCanTrackService();
        instance.watchService("anyID");
        capturingAnalytics = new CapturingAnalytics();
        messageService = new MessageService(instance, capturingAnalytics);
    }

}