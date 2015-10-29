package fakes;

import java.util.ArrayList;

import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;

public class JournalingAnalytics implements Analytics {

    private final String name;
    public final ArrayList<String> journal;

    public JournalingAnalytics(String name, ArrayList<String> journal) {

        this.name = name;
        this.journal = journal;
    }

    public JournalingAnalytics() {
        this("",new ArrayList<String>());
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
