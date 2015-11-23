package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesQueryViewModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RemembersDeparturesQuery {

    private DeparturesPresenter departuresPresenter;
    private Direction expectedDirection;
    private Station expectedStation;

    @Before
    public void setUp() throws Exception {

        expectedDirection = Direction.to(new Station("Salford Crescent","SLD"));
        expectedStation = new Station("Chorley","CRL");

        CanQueryDepartures tmt = new CanQueryDepartures() {
            @Override
            public void departures(Station at, Direction direction, DepartureQueryListener result) {

            }

            @Override
            public DepartureQuery lastQuery() {
                return new DepartureQuery(expectedStation,expectedDirection);
            }

            @Override
            public void addDepartureQueryListener(DepartureQueryListener departureQueryListener) {

            }
        };
        departuresPresenter = new DeparturesPresenter(tmt);

    }


    @Test
    public void generatesADepartureQueryFromTheViewModel() {
        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        departuresPresenter.attach(departuresQueryView);

        DepartureQuery expectedQuery = new DepartureQuery(expectedStation,expectedDirection);
        assertThat(departuresQueryView.departuresQueryViewModel.departuresQuery(), is(expectedQuery));
    }

    @Test
    public void swapsStationsInQuery() {
        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        departuresPresenter.attach(departuresQueryView);

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
