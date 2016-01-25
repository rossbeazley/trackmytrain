package uk.co.rossbeazley.trackmytrain.android.wear;

public class CapturingServiceViewNavigationController implements ServiceViewNavigationController {

    public static final String STARTED = "Started";
    public String trackingIs = "UNKNOWN";


    @Override
    public void trackingStarted() {
        trackingIs = STARTED;
    }
}