package uk.co.rossbeazley.trackmytrain.android.analytics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import fakes.JournalingAnalytics;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

public class AnalyticsTest {


    private ArrayList<String> journal;
    private AnalyticsMultiplexer analyticsMultiplexer;

    @Before
    public void setUp() throws Exception {
        journal = new ArrayList<>();
        analyticsMultiplexer = new AnalyticsMultiplexer();
        analyticsMultiplexer.register(new JournalingAnalytics("one", journal));
        analyticsMultiplexer.register(new JournalingAnalytics("two", journal));

    }

    @Test
    public void
    multiplexingAnalticsEvents() {
        analyticsMultiplexer.event(anyEvent());
        assertThat(journal, hasItems("oneevent", "twoevent"));
    }

    @Test
    public void
    multiplexingAnalticsPageView() {
        analyticsMultiplexer.pageView(anyPageName());
        assertThat(journal, hasItems("onepageview","twopageview"));
    }

    @Test
    public void
    multiplexingAnalticsTiming() {
        analyticsMultiplexer.timing(0,"","");
        assertThat(journal, hasItems("onetiming", "twotiming"));
    }

    private String anyPageName() {
        return "any";
    }

    private Analytics.EventTrack anyEvent() {
        return new Analytics.EventTrack("","");
    }

}