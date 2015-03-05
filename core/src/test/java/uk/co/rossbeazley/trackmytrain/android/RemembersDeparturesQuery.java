package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RemembersDeparturesQuery {

    @Test
    public void theOneWhereTheDirectionIsRemembered() {
        TrackMyTrain tmt;
        tmt = TestDataBuilder.TMTBuilder()
                .build();

        Direction expectedDirection = Direction.to(Station.fromString("SLD"));
        Station at = Station.fromString("ANY");
        tmt.departures(at,expectedDirection);

        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        tmt.attach(departuresQueryView);

        assertThat(departuresQueryView.direction, is(expectedDirection));

    }

    @Test
    public void theOneWhereTheAtIsRemembered() {
        TrackMyTrain tmt;
        tmt = TestDataBuilder.TMTBuilder()
                .build();

        Direction anyDirection = Direction.to(Station.fromString("SLD"));
        Station expectedStation = Station.fromString("CRL");
        tmt.departures(expectedStation,anyDirection);

        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        tmt.attach(departuresQueryView);

        assertThat(departuresQueryView.at, is(expectedStation));
    }



    private static class CapturingDeparturesQueryView implements DeparturesQueryView {
        public Station at;
        public Direction direction;

        @Override
        public void present(Station at, Direction direction) {
            this.at = at;
            this.direction = direction;
        }

    }
}
