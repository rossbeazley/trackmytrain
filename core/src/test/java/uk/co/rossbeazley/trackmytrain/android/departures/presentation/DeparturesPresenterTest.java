package uk.co.rossbeazley.trackmytrain.android.departures.presentation;

import org.junit.Test;

import java.util.Arrays;

import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.CapturingDeparturesView;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.DepartureQuery;
import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DeparturesPresenterTest {



    @Test
    public void theOneWhereWeRequestDetailsOfAServiceAndWeStartLoading() {

        CapturingDeparturesView departuresView = new CapturingDeparturesView();

        CanQueryDepartures canQueryDepartures = new CanQueryDepartures() {
            @Override
            public void departures(Station at, Direction direction, Result success) {
                success.loading();
            }

            @Override
            public DepartureQuery lastQuery() {
                return null;
            }
        };
        DeparturesPresenter tmt = new DeparturesPresenter(canQueryDepartures);

        final Station fromStation = TestDataBuilder.anyStation();
        final Station toStation = TestDataBuilder.anyStation();

        tmt.attach(departuresView);

        Station at = fromStation;
        Direction direction = Direction.to(toStation);

        tmt.departures(at, direction);

        assertThat(departuresView.isLoading, is(true));
    }

    @Test
    public void theOneWhereWeRequestDetailsOfAServiceAndTheResultsAreDisplayed() {

        CapturingDeparturesView departuresView = new CapturingDeparturesView();
/**
 * connascence of algorithm with all this json and url strings :S
 */

        final Train train1, train2;
        train1 = new Train("aN5S6pak5nKFawy0sXb65Q==", "On time", "21:39", "2", false);
        train2 = new Train("EAG/q7qfInIUZyPhCdwQKw==", "On time", "22:38", "2", false);

        final DeparturesViewModel expectedList = TrainViewModel.list(Arrays.asList(train1, train2));

        CanQueryDepartures stubCanQueryDepartures = new CanQueryDepartures() {
            @Override
            public void departures(Station at, Direction direction, Result success) {
                success.success(Arrays.asList(train1, train2));
            }

            @Override
            public DepartureQuery lastQuery() {
                return null;
            }
        };
        DeparturesPresenter departuresPresenter = new DeparturesPresenter(stubCanQueryDepartures);
        departuresPresenter.attach(departuresView);

        Station at = Station.fromString("SLD");
        Direction direction = Direction.to(Station.fromString("CRL"));

        departuresPresenter.departures(at, direction);

        assertThat(departuresView.trainList, is(expectedList));
    }

    @Test
    public void departuresToFromRequestRendersToString() {
        DeparturesFromToRequest req = new DeparturesFromToRequest(Station.fromString("MCO"),Station.fromString("SLD"));
        assertThat(req.asUrlString(),is(DeparturesFromToRequest.WS_URL_ROOT + "departures/MCO/to/SLD"));
    }
}