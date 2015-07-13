package uk.co.rossbeazley.trackmytrain.android.mobile;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class GoogleAnalytics implements Analytics {
    private final Tracker tracker;

    public GoogleAnalytics(Tracker tracker) {
        this.tracker = tracker;
    }

    @Override
    public void timing(long millis, String category, String variable) {
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory(category)
                .setValue(millis)
                .setVariable(variable)
                .build());
    }

    @Override
    public void event(String category, String label) {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setLabel(label)
                .build());
    }

}
