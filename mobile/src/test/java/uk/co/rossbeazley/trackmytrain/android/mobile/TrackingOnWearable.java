package uk.co.rossbeazley.trackmytrain.android.mobile;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.MessagingTrackingPresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.StoppedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.TrackedServiceMessage;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackingOnWearable {

    private MessagingTrackingPresenter messagingTrackingPresenter;
    private CapturingPostman postman;

    @Before
    public void setup() {
        postman = new CapturingPostman();
        messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
    }

    @Test
    public void
    sendsMessageWhenTrackingStarts() {
        messagingTrackingPresenter.trackingStarted();
        Postman.Message expectedMessage = new StartedTrackingMessage();
        assertThat(postman.broadcasts, hasItem(expectedMessage));
    }

    @Test
    public void
    sendsMessageWhenTrackingStops() {
        messagingTrackingPresenter.hide();
        Postman.Message expectedMessage = new StoppedTrackingMessage();
        assertThat(postman.broadcasts, hasItem(expectedMessage));
    }

    @Test
    public void
    sendsDetailsOfTrackedService() {
        TrainViewModel train = TestDataBuilder.anyTrainViewModel();
        messagingTrackingPresenter.present(train);
        Postman.Message expectedMessage = new TrackedServiceMessage(train);
        assertThat(postman.broadcasts, hasItem(expectedMessage));
    }


}
