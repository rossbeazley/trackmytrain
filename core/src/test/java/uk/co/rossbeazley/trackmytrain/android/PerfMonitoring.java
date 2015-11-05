package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import fakes.CapturingAnalytics;
import fakes.ClockStub;
import fakes.SlowRequestMapNetworkClient;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class PerfMonitoring {

    private TrackMyTrain tmt;
    private SlowRequestMapNetworkClient networkClient;
    private CapturingAnalytics tracker;
    private ClockStub clock;

    @Before
    public void
    notTrackingATrainWithASlowNetwork() {

        networkClient = new SlowRequestMapNetworkClient(new HashMap<NetworkClient.Request, String>());

        clock = new ClockStub();
        tracker = new CapturingAnalytics();

        tmt = TestDataBuilder.TMTBuilder()
                .with(networkClient)
                .with(tracker)
                .with(clock)
                .build();
    }

    @Test
    public void
    theOneWhereWeCompleteASearchAndTheTimingInformationIsRecorded() {

        final String category = "DeparturesQuery";
        final String variable = "DeparturesQuery.load";
        CapturingAnalytics.TimingTrack expected = new CapturingAnalytics.TimingTrack(1337,category,variable);

        tmt.departures(TestDataBuilder.anyStation(), TestDataBuilder.anyDirection(), new NullDepartureQueryListener());

        clock.advanceBy(1337);
        networkClient.completeRequest();

        assertThat(tracker.timing, hasItem(expected));
    }

    private static class NullDepartureQueryListener implements CanQueryDepartures.DepartureQueryListener {
        @Override
        public void success(List<Train> expectedList) {

        }

        @Override
        public void error(TMTError tmtError) {

        }

        @Override
        public void loading() {

        }
    }
}