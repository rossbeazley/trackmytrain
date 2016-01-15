package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotificationsDontStartTests {


    @Test
    public void
    whenAllUIDetachesWhenNotTracking_NONotificationServiceIsStarted() {


        CapturingNotificationService service = new CapturingNotificationService();

        HostNode hostNode = new HostNode();
        WearApp wearApp = new WearApp(hostNode, new CapturingPostman(), service);
        final CapturingServiceView anyView = new CapturingServiceView();
        wearApp.attach(anyView);

        wearApp.detach(anyView);

        assertThat(service.state, is(CapturingNotificationService.UNKNOWN));

    }







}