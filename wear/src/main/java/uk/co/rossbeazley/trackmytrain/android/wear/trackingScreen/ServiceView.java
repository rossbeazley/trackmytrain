package uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen;

import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;

/**
* Created by beazlr02 on 17/02/2015.
*/
public interface ServiceView {

    void present(TrainViewModel train);

    void hide();

}
