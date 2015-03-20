package uk.co.rossbeazley.trackmytrain.android;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;

/**
* Created by beazlr02 on 05/03/2015.
*/
public class CapturingDeparturesView implements DeparturesView {

    public DeparturesViewModel trainList;

    @Override
    public void present(DeparturesViewModel trains) {
        trainList = trains;
    }

}
