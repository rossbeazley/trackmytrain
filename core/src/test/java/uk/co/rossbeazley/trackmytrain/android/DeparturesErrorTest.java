package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import fakes.*;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

public class DeparturesErrorTest {


    private Station fromStation;
    private Station toStation;
    private TrackMyTrain tmt;
    private Station at;
    private Direction direction;

    @Before
    public void setUp() throws Exception {
        fromStation = fakes.TestDataBuilder.anyStation();
        toStation = fakes.TestDataBuilder.anyStation();
        tmt = fakes.TestDataBuilder.TMTBuilder()
                .with(new NetworkClient() {
                    @Override
                    public void get(Request request, Response response) {
                        response.error("404");
                    }
                })
                .build();
        at = fromStation;
        direction = Direction.to(toStation);
    }

    @Test
    public void networkErrorWhenRequestingServiceDetails() {

        JournalingDepartureQueryListener journalingDepartureQueryListener = new JournalingDepartureQueryListener();
        tmt.departures(at, direction, journalingDepartureQueryListener);

        assertThat(journalingDepartureQueryListener.errors, hasItem(new TMTError("404")));
    }

    @Test
    public void
    networkErrorThroughObservationOfTMTWhenRequestingServiceDetails()
    {

        JournalingDepartureQueryListener departureQueryListener = new JournalingDepartureQueryListener();
        tmt.addDepartureQueryListener(departureQueryListener);

        tmt.departures(at, direction, new JournalingDepartureQueryListener());

        assertThat(departureQueryListener.errors, hasItem(new TMTError("404")));
    }

}