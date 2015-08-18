package uk.co.rossbeazley.trackmytrain.android.mobile.analytics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class GoogleAnalytics implements Analytics {
    private final Tracker tracker;

    public GoogleAnalytics(Tracker tracker) {
        this.tracker = tracker;
    }

    @Override
    public void timing(long millis, String category, String variable) {
        tracker.setPage("none");
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory(category)
                .setValue(millis)
                .setVariable(variable)
                .build());
    }

    @Override
    public void event(EventTrack eventTrack) {
        tracker.setPage("none");
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(eventTrack.category)
                .setAction(eventTrack.label)
                .setLabel(eventTrack.label)
                .build());
    }

    @Override
    public void pageView(String pageName) {
        tracker.setPage(pageName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
