package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import fakes.JournalingDepartureQueryListener;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RemembersDeparturesQuery {

    private Direction expectedDirection;
    private Station expectedStation;
    private KeyValuePersistence keyValuePersistence;
    private DepartureQuery actualQuery;

    @Before
    public void setUp() throws Exception {
        keyValuePersistence = new HashMapKeyValuePersistence();
        TrackMyTrain tmt = TestDataBuilder.TMTBuilder()
                .with(keyValuePersistence)
                .build();

        expectedDirection = Direction.to(new Station("Salford Crescent","SLD"));
        expectedStation = new Station("Chorley","CRL");

        tmt.departures(expectedStation,expectedDirection,new JournalingDepartureQueryListener());

        actualQuery = tmt.lastQuery();
    }

    @Test
    public void theOneWhereTheDirectionIsRemembered() {
        assertThat(actualQuery.direction(), is(expectedDirection));

    }

    @Test
    public void theOneWhereTheAtIsRemembered() {

        assertThat(actualQuery.at(), is(expectedStation));
    }


    @Test
    public void theOneWhereTheDirectionIsRememberedBetweenSessions() {
        TrackMyTrain tmt = TestDataBuilder.TMTBuilder()
                .with(keyValuePersistence)
                .build();

        assertThat(tmt.lastQuery().direction(), is(expectedDirection));

    }


    @Test
    public void theOneWhereTheAtIsRememberedAcrossSessions() {
        TrackMyTrain tmt = TestDataBuilder.TMTBuilder()
                .with(keyValuePersistence)
                .build();
        assertThat(tmt.lastQuery().at(), is(expectedStation));
    }


    private static class CapturingDeparturesQueryView implements DeparturesQueryView {
        public Station at;
        public Direction direction;
        public DeparturesQueryViewModel departuresQueryViewModel;

        @Override
        public void present(DeparturesQueryViewModel departuresQueryViewModel) {
            this.departuresQueryViewModel = departuresQueryViewModel;
            this.at = departuresQueryViewModel.getAt();
            this.direction = departuresQueryViewModel.getDirection();
        }

    }
}
