package uk.co.rossbeazley.trackmytrain.android.trackedService;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

/**
* Created by beazlr02 on 17/02/2015.
*/
public interface ServiceView {

    void present(TrainViewModel train);

    void hide();
}