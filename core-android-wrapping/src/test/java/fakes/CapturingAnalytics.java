package fakes;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;

public class CapturingAnalytics implements Analytics {
    public List<EventTrack> events = new ArrayList<>();
    public List<TimingTrack> timing = new ArrayList<>();
    public List<String> pageViews = new ArrayList<>();

    @Override
    public void timing(long millis, String category, String variable) {
        timing.add(new TimingTrack(millis,category,variable));
    }

    @Override
    public void event(EventTrack eventTrack) {
        events.add(eventTrack);
    }

    @Override
    public void pageView(String pageName) {
        pageViews.add(pageName);
    }

    public static class TimingTrack {
        private final long millis;
        private final String category;
        private final String variable;

        public TimingTrack(long millis, String category, String variable) {

            this.millis = millis;
            this.category = category;
            this.variable = variable;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TimingTrack that = (TimingTrack) o;

            if (millis != that.millis) return false;
            if (!category.equals(that.category)) return false;
            return variable.equals(that.variable);

        }

        @Override
        public int hashCode() {
            int result = (int) (millis ^ (millis >>> 32));
            result = 31 * result + category.hashCode();
            result = 31 * result + variable.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "TimingTrack{" +
                    "millis=" + millis +
                    ", category='" + category + '\'' +
                    ", variable='" + variable + '\'' +
                    '}';
        }
    }
}
