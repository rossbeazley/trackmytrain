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

public class TrackingAServiceAnalyticsAndPerfMonitoring {

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
        int anyAmountOfTimeInMillis = 1337;
        CapturingAnalytics.TimingTrack expected = new CapturingAnalytics.TimingTrack(anyAmountOfTimeInMillis,category,variable);

        tmt.departures(TestDataBuilder.anyStation(), TestDataBuilder.anyDirection(), new NullDepartureQueryListener());

        clock.advanceBy(anyAmountOfTimeInMillis);
        networkClient.completeRequest();

        assertThat(tracker.timing, hasItem(expected));
    }


    @Test
    public void
    theOneWhereASearchErrorsAndTheTimingInformationIsRecorded() {

        final String category = "DeparturesQuery";
        final String variable = "DeparturesQuery.error";
        CapturingAnalytics.TimingTrack expected = new CapturingAnalytics.TimingTrack(1337,category,variable);

        tmt.departures(TestDataBuilder.anyStation(), TestDataBuilder.anyDirection(), new NullDepartureQueryListener());

        clock.advanceBy(1337);
        networkClient.errorRequest();

        assertThat(tracker.timing, hasItem(expected));
    }
    /**
     *

     @Override
     public void trackingStopped() {
     tracker.pageView("Tracking_Stopped");
     }
     */
    @Test
    public void
    trackingStartsPageViewRecorded() {

        tmt.watch(TestDataBuilder.anyTrainId());

        String expected = "Tracking_Loading";
        assertThat(tracker.pageViews, hasItem(expected));
    }

    @Test
    public void
    trackedServiceIsUpdatedPageViewRecorded() {

        tmt.watch(TestDataBuilder.anyTrainId());

        networkClient.completeRequestWith(TestDataBuilder.anyTrainJson());

        String expected = "Tracking_Result";
        assertThat(tracker.pageViews, hasItem(expected));
    }

    @Test
    public void
    trackingIsStoppedPageViewRecorded() {

        tmt.watch(TestDataBuilder.anyTrainId());

        networkClient.completeRequestWith(TestDataBuilder.anyTrainJson());

        tmt.unwatch();

        String expected = "Tracking_Stopped";
        assertThat(tracker.pageViews, hasItem(expected));
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