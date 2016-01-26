package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.wear.CapturingPostman;
import uk.co.rossbeazley.trackmytrain.android.wear.StopTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.WearApp;
import uk.co.rossbeazley.trackmytrain.android.wear.comms.HostNode;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

public class StopTracking {


    private CapturingPostman capturingPostman;
    private WearApp wearApp;

    @Before
    public void setUp() throws Exception {
        capturingPostman = new CapturingPostman();
        wearApp = new WearApp(new HostNode(), capturingPostman, new CapturingNotificationServiceService());
    }

    @Test
    public void wearAppStopsTracking() {
        wearApp.unwatch();

        assertThat(capturingPostman.broadcasts, hasItem(new StopTrackingMessage()));
    }
}
