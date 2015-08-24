package uk.co.rossbeazley.trackmytrain.android.analytics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

    static class JournalingAnalytics implements Analytics {

        private final String name;
        private final ArrayList<String> journal;

        public JournalingAnalytics(String name, ArrayList<String> journal) {

            this.name = name;
            this.journal = journal;
        }

        @Override
        public void timing(long millis, String category, String variable) {
            journal.add(name+"timing");
        }

        @Override
        public void event(EventTrack eventTrack) {
            journal.add(name+"event");
        }

        @Override
        public void pageView(String pageName) {
            journal.add(name+"pageview");
        }
    }

    private class AnalyticsMultiplexer implements Analytics{

        ArrayList<Analytics> things = new ArrayList<>();

        @Override
        public void timing(long millis, String category, String variable) {
            for (Analytics t :things) {
                t.timing(millis,category,variable);
            }
        }

        @Override
        public void event(EventTrack eventTrack) {
            for (Analytics t : things) {
                t.event(eventTrack);
            }
        }

        @Override
        public void pageView(String pageName) {
            for (Analytics t :things) {
                t.pageView(pageName);
            }
        }

        public void register(Analytics analytics) {
            things.add(analytics);
        }
    }
}