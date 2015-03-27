package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;

/**
* Created by beazlr02 on 05/03/2015.
*/
public class CapturingDeparturesView implements DeparturesView {

    public DeparturesViewModel trainList;
    public boolean isLoading = false;
    public String error;

    @Override
    public void present(DeparturesViewModel trains) {
        isLoading = false;
        trainList = trains;
    }

    @Override
    public void loading() {
        isLoading = true;
        trainList = null;
    }

    @Override
    public void error(String error) {
        this.error = error;
    }

}
