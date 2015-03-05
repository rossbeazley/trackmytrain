package uk.co.rossbeazley.trackmytrain.android;

import org.junit.Test;

import java.util.List;

public class RemembersDeparturesQuery {

    @Test
    public void theOneWhereTheFromStationIsRemembered() {
        TrackMyTrain tmt;
        tmt = new TMTBuilder()
                .build();

        tmt.attach(new DeparturesView() {
            @Override
            public void present(List<TrainViewModel> trains) {

            }
        });


    }
}
