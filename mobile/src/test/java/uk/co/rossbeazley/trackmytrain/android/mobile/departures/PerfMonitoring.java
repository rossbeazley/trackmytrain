package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import fakes.CapturingAnalytics;
import fakes.SlowRequestMapNetworkClient;
import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class PerfMonitoring {

    private TrackMyTrain tmt;
    private SlowRequestMapNetworkClient networkClient;
    private CapturingAnalytics tracker;
    private MyClock clock;

    @Before
    public void
    notTrackingATrainWithASlowNetwork() {


        final Station fromStation = TestDataBuilder.anyStation();
        final Station toStation = TestDataBuilder.anyStation();
        networkClient = new SlowRequestMapNetworkClient(new HashMap<NetworkClient.Request, String>());


        clock = new MyClock();
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

        CanQueryDepartures.DepartureQueryListener perf = new PerfMonitoringView(tracker, clock);

        final String category = "DeparturesQuery";
        final String variable = "DeparturesQuery.load";
        CapturingAnalytics.TimingTrack expected = new CapturingAnalytics.TimingTrack(1337,category,variable);

        tmt.departures(TestDataBuilder.anyStation(), TestDataBuilder.anyDirection(), new CanQueryDepartures.DepartureQueryListener() {
            @Override
            public void success(List<Train> expectedList) {

            }

            @Override
            public void error(TMTError tmtError) {

            }

            @Override
            public void loading() {

            }
        });

        clock.advanceBy(1337);
        networkClient.completeRequest();


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