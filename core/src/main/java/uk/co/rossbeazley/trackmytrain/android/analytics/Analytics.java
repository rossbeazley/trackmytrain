package uk.co.rossbeazley.trackmytrain.android.analytics;

import java.util.Objects;

public interface Analytics {
    void timing(long millis, String category, String variable);

    void event(EventTrack eventTrack);

    void pageView(String pageName);

    static class EventTrack {
        final String category;
        final String label;

        public EventTrack(String category, String label) {
            this.category = category;
            this.label = label;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EventTrack that = (EventTrack) o;
            return Objects.equals(category, that.category) &&
                    Objects.equals(label, that.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, label);
        }

        @Override
        public String toString() {
            return "EventTrack{" +
                    "category='" + category + '\'' +
                    ", label='" + label + '\'' +
                    '}';
        }
    }
}
