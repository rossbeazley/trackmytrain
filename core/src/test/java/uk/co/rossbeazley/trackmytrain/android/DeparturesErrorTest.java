package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Ignore;
import org.junit.Test;

import fakes.JournalingDepartureQueryListener;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

public class DeparturesErrorTest {



    @Test
    public void networkErrorWhenRequestingServiceDetails() {

        final Station fromStation = TestDataBuilder.anyStation();
        final Station toStation = TestDataBuilder.anyStation();

        TrackMyTrain tmt = TestDataBuilder.TMTBuilder()
                .with(new NetworkClient() {
                    @Override
                    public void get(Request request, Response response) {
                        response.error("404");
                    }
                })
                .build();

        Station at = fromStation;
        Direction direction = Direction.to(toStation);
        JournalingDepartureQueryListener journalingDepartureQueryListener = new JournalingDepartureQueryListener();
        tmt.departures(at, direction, journalingDepartureQueryListener);

        assertThat(journalingDepartureQueryListener.errors, hasItem(new TMTError("404")));
    }

    @Test @Ignore("TODO")
    public void
    networkErrorThroughObservationOfTMTWhenRequestingServiceDetails()
    {

    }

}