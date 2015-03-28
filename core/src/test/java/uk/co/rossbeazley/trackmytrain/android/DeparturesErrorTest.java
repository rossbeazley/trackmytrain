package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.departures.Direction;
import uk.co.rossbeazley.trackmytrain.android.departures.Station;

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
                    public void requestString(Request request, Response response) {
                        response.error("404");
                    }
                })
                .build();

        tmt.attach(departuresView);

        Station at = fromStation;
        Direction direction = Direction.to(toStation);
        tmt.departures(at, direction);

        assertThat(departuresView.error, is(new TMTError("404")));
    }

}