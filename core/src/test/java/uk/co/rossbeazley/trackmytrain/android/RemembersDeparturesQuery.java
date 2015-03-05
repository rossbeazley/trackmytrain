package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThat;

public class RemembersDeparturesQuery {

    @Test
    public void theOneWhereTheFromStationIsRemembered() {
        TrackMyTrain tmt;
        tmt = new TMTBuilder()
                .build();


        CapturingDeparturesQueryView departuresQueryView = new CapturingDeparturesQueryView();
        tmt.attach(departuresQueryView);


        //assertThat(departuresQueryView)

    }

    private static class CapturingDeparturesQueryView implements DeparturesQueryView {
        @Override
        public void present(Station at, Direction direction) {

        }

    }
}
