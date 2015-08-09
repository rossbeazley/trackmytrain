package uk.co.rossbeazley.trackmytrain.android.wear;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class TheApplicationWill {

    private CapturingPostman capturingPostman;
    private WearApp wearApp;

    @Before
    public void setUp() throws Exception {
        capturingPostman = new CapturingPostman();
        wearApp = new WearApp(new HostNode(), capturingPostman);
    }

    @Test
    public void
    announceCreatedEventOverAnalytics() {

        final Postman.Message expectedMessage = new AnalyticsEventMessage("WEAR-CREATED", "CREATED");
        assertThat(capturingPostman.broadcasts, hasItem(expectedMessage));
    }

    @Test
    public void
    sendsAnalyticsEventWhenAViewAttaches() {
        final AnyOldView serviceView = new AnyOldView();
        wearApp.attach(serviceView);
        Postman.Message expectedMessage = new AnalyticsEventMessage("WEAR-SERVICEVIEW-ATTACHED", serviceView.getClass().getSimpleName());
        assertThat(capturingPostman.broadcasts, CoreMatchers.hasItem(expectedMessage));
    }

    public static final class AnyOldView implements ServiceView {
        @Override
        public void present(TrainViewModel train) {

        }

        @Override
        public void hide() {

        }

        @Override
        public void trackingStarted() {

        }
    }

}