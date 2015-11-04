package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import org.junit.Test;

import fakes.CapturingAnalytics;
import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class PerfMonitoring {

    @Test
    public void
    theOneWhereWeCompleteASearchAndTheTimingInformationIsRecorded() {

        MyClock clock = new MyClock();
        CapturingAnalytics tracker = new CapturingAnalytics();
        CanQueryDepartures.DepartureQueryListener perf = new PerfMonitoringView(tracker, clock);

        final String category = "DeparturesQuery";
        final String variable = "DeparturesQuery.load";
        CapturingAnalytics.TimingTrack expected = new CapturingAnalytics.TimingTrack(1337,category,variable);


        perf.loading();
        clock.advanceBy(1337);
        perf.success(TestDataBuilder.anyTrains());

        assertThat(tracker.timing, hasItem(expected));
    }

    private static class MyClock implements Clock {

        long nextReportedTime;

        public MyClock() {
            nextReportedTime = 0;
        }

        public void advanceBy(int i) {
            nextReportedTime+=i;
        }

        @Override
        public long time() {
            return nextReportedTime;
        }
    }
}