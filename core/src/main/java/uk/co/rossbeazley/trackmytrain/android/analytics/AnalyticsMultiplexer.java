package uk.co.rossbeazley.trackmytrain.android.analytics;

import java.util.ArrayList;

/**
 * Created by beazlr02 on 24/08/15.
 */
public class AnalyticsMultiplexer implements Analytics {

    ArrayList<Analytics> things = new ArrayList<>();

    @Override
    public void timing(long millis, String category, String variable) {
        for (Analytics t : things) {
            t.timing(millis, category, variable);
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
        for (Analytics t : things) {
            t.pageView(pageName);
        }
    }

    public void register(Analytics analytics) {
        things.add(analytics);
    }
}
