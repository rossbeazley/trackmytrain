package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fakes.JournalingDepartureQueryListener;
import uk.co.rossbeazley.trackmytrain.android.HashMapKeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.KeyValuePersistence;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

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


        actualQuery = tmt.lastQuery();
    }


    @Test @Ignore("TODO")
    public void generatesADepartureQueryFromTheViewModel() {
        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();

        DepartureQuery expectedQuery = new DepartureQuery(expectedStation,expectedDirection);
        assertThat(departuresQueryView.departuresQueryViewModel.departuresQuery(), is(expectedQuery));
    }

    @Test @Ignore("TODO")
    public void swapsStationsInQuery() {
        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();

        departuresQueryView.departuresQueryViewModel.swapStations();

        Station swappedStation = expectedDirection.station();
        Direction swappedDirection = Direction.to(expectedStation);

        DepartureQuery expectedQuery = new DepartureQuery(swappedStation,swappedDirection);
        assertThat(departuresQueryView.departuresQueryViewModel.departuresQuery(), is(expectedQuery));
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
