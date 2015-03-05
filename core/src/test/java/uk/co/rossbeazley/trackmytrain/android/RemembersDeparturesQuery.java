package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RemembersDeparturesQuery {

    private TrackMyTrain tmt;
    private Direction expectedDirection;
    private Station expectedStation;
    private KeyValuePersistence keyValuePersistence;

    @Before
    public void setUp() throws Exception {
        keyValuePersistence = new HashMapKeyValuePersistence();
        tmt = TestDataBuilder.TMTBuilder()
                .with(keyValuePersistence)
                .build();
        expectedDirection = Direction.to(Station.fromString("SLD"));
        expectedStation = Station.fromString("CRL");

        tmt.departures(expectedStation, expectedDirection);
    }

    @Test
    public void theOneWhereTheDirectionIsRemembered() {
        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        tmt.attach(departuresQueryView);

        assertThat(departuresQueryView.direction, is(expectedDirection));

    }

    @Test
    public void theOneWhereTheAtIsRemembered() {
        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        tmt.attach(departuresQueryView);

        assertThat(departuresQueryView.at, is(expectedStation));
    }


    @Test
    public void theOneWhereTheDirectionIsRememberedBetweenSessions() {

        tmt = TestDataBuilder.TMTBuilder()
                .with(keyValuePersistence)
                .build();

        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        tmt.attach(departuresQueryView);

        assertThat(departuresQueryView.direction, is(expectedDirection));

    }


    @Test
    public void theOneWhereTheAtIsRememberedAcrossSessions() {

        tmt = TestDataBuilder.TMTBuilder()
                .with(keyValuePersistence)
                .build();

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
