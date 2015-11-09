package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DeparturesErrorTest {



    @Test
    public void networkErrorWhenRequestingServiceDetails() {

        CapturingDeparturesView departuresView = new CapturingDeparturesView();

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

        DeparturesPresenter departuresPresenter = new DeparturesPresenter(tmt);

        departuresPresenter.attach(departuresView);

        Station at = fromStation;
        Direction direction = Direction.to(toStation);
        departuresPresenter.departures(at, direction);

        assertThat(departuresView.error, is(new TMTError("404")));
    }

}