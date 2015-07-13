package uk.co.rossbeazley.trackmytrain.android.mobile;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.MessagingTrackingPresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;
import uk.co.rossbeazley.trackmytrain.android.wear.StoppedTrackingMessage;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackingOnWearable {

    @Test
    public void
    sendsMessageWhenTrackingStarts() {

        new TestTrackMyTrainApp();
        CapturingPostman postman = new CapturingPostman();
        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");

        Postman.Message expectedMessage = new StartedTrackingMessage();

        assertThat(postman.broadcasts, hasItem(expectedMessage));
    }

    @Test
    public void
    dosntSendsMessageWhenTrackingUpdated() {

        new TestTrackMyTrainApp();
        CapturingPostman postman = new CapturingPostman();
        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");
        postman.clearBroadcasts();
        TestTrackMyTrainApp.executorService.command.run();


        assertThat(postman.broadcasts, not(hasItem(new StartedTrackingMessage())));
    }


    @Test
    public void
    sendsMessageWhenTrackingANewService() {

        new TestTrackMyTrainApp();
        CapturingPostman postman = new CapturingPostman();
        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");
        postman.clearBroadcasts();
        TrackMyTrainApp.instance.unwatch();
        TrackMyTrainApp.instance.watch("2");

        Postman.Message expectedMessage = new StartedTrackingMessage();

        assertThat(postman.broadcasts, hasItem(expectedMessage));
    }


    @Test
    public void
    sendsMessageWhenTrackingStops() {

        new TestTrackMyTrainApp();
        CapturingPostman postman = new CapturingPostman();
        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");
        postman.clearBroadcasts();
        TrackMyTrainApp.instance.unwatch();

        Postman.Message expectedMessage = new StoppedTrackingMessage();

        assertThat(postman.broadcasts, hasItem(expectedMessage));
    }

    @Test
    public void
    sendsDetailsOfTrackedService() {

        new TestTrackMyTrainApp();
        CapturingPostman postman = new CapturingPostman();

        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");
        Postman.Message expectedMessage = new TrackedServiceMessage(new TrainViewModel(new Train("2", "10:00", "09:00", "1")));
        assertThat(postman.broadcasts, hasItem(expectedMessage));
    }


}
