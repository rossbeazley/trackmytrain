package fakes;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.CanQueryDepartures;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class JournalingDepartureQueryListener implements CanQueryDepartures.DepartureQueryListener {
    public List<TMTError> errors = new ArrayList<>();

    @Override
    public void success(List<Train> expectedList) {

    }

    @Override
    public void error(TMTError tmtError) {
        this.errors.add(tmtError);
    }

    @Override
    public void loading() {

    }
}
