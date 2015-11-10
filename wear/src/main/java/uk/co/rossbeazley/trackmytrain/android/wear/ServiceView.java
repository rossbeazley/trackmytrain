package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

/**
* Created by beazlr02 on 17/02/2015.
*/
public interface ServiceView {

    void present(TrainViewModel train);

    void hide();

    void trackingStarted();
}
