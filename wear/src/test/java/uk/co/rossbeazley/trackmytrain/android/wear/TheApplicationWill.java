package uk.co.rossbeazley.trackmytrain.android.wear;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class TheApplicationWill {

    @Test
    public void
    announceCreatedEventOverAnalytics() {

        final CapturingPostman capturingPostman = new CapturingPostman();

        new WearApp(new HostNode(), capturingPostman);

        final Postman.Message expectedMessage = new AnalyticsEventMessage("CREATED", "");
        assertThat(capturingPostman.broadcasts, hasItem(expectedMessage));
    }

}