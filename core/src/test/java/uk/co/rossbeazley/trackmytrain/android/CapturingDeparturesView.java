package uk.co.rossbeazley.trackmytrain.android;

import java.util.List;

/**
* Created by beazlr02 on 05/03/2015.
*/
public class CapturingDeparturesView implements DeparturesView {

    public List<TrainViewModel> trainList;

    @Override
    public void present(List<TrainViewModel> trains) {
        trainList = trains;
    }

}