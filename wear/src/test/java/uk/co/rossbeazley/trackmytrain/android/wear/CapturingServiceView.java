package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen.ServiceView;

public class CapturingServiceView implements ServiceView {
    public static final String HIDDEN = "Hidden";
    public static final String VISIBLE = "Visible";
    public String visibility = "UNKNOWN";

    public TrainViewModel serviceDisplayed;

    @Override
    public void present(TrainViewModel train) {
        visibility = VISIBLE;
        serviceDisplayed = train;
    }

    @Override
    public void hide() {
        serviceDisplayed = null;
        visibility = HIDDEN;
    }

}