package uk.co.rossbeazley.trackmytrain.android.analytics;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

public class AnalyticsTest {


    @Test
    public void
    multiplexingAnalticsEvents() {

        ArrayList<String> journal = new ArrayList<>();

        AnalyticsMultiplexer analyticsMultiplexer = new AnalyticsMultiplexer();
        analyticsMultiplexer.register(new JournalingAnalytics("one", journal));
        analyticsMultiplexer.register(new JournalingAnalytics("two", journal));

        analyticsMultiplexer.event(anyEvent());

        assertThat(journal, hasItems("one", "two"));
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

        }

        @Override
        public void event(EventTrack eventTrack) {
            journal.add(name);
        }

        @Override
        public void pageView(String pageName) {

        }
    }

    private class AnalyticsMultiplexer implements Analytics{

        ArrayList<Analytics> things = new ArrayList<>();

        @Override
        public void timing(long millis, String category, String variable) {

        }

        @Override
        public void event(EventTrack eventTrack) {
            for (Analytics t : things) {
                t.event(eventTrack);
            }
        }

        @Override
        public void pageView(String pageName) {

        }

        public void register(Analytics analytics) {
            things.add(analytics);
        }
    }
}