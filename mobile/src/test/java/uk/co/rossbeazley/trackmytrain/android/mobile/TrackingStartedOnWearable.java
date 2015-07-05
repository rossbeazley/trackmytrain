package uk.co.rossbeazley.trackmytrain.android.mobile;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.IAmBaseMessage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrackingStartedOnWearable {

    @Test
    public void
    sendsMessageWhenTrackingStarts() {

        new TestTrackMyTrainApp();
        CapturePostman postman = new CapturePostman();
        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");

        Postman.Message expectedMessage = new IAmBaseMessage();
        Postman.Message messageDelivered = postman.messageBroadcast;
        assertThat(messageDelivered, is(expectedMessage));
    }

    @Test
    public void
    dosntSendsMessageWhenTrackingUpdated() {

        new TestTrackMyTrainApp();
        CapturePostman postman = new CapturePostman();
        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");
        postman.messageBroadcast = null;
        TestTrackMyTrainApp.executorService.command.run();

        Postman.Message messageDelivered = postman.messageBroadcast;
        assertThat(messageDelivered, is(nullValue()));
    }


    @Test
    public void
    sendsMessageWhenTrackingANewService() {

        new TestTrackMyTrainApp();
        CapturePostman postman = new CapturePostman();
        final MessagingTrackingPresenter messagingTrackingPresenter = new MessagingTrackingPresenter(postman);
        TestTrackMyTrainApp.instance.attach(messagingTrackingPresenter);

        TrackMyTrainApp.instance.watch("2");
        postman.messageBroadcast = null;
        TrackMyTrainApp.instance.unwatch();
        TrackMyTrainApp.instance.watch("2");

        Postman.Message expectedMessage = new IAmBaseMessage();
        Postman.Message messageDelivered = postman.messageBroadcast;
        assertThat(messageDelivered, is(expectedMessage));
    }


    private class MessagingTrackingPresenter implements ServiceView {
        private final Postman postman;

        public MessagingTrackingPresenter(Postman postman) {
            this.postman = postman;
        }

        @Override
        public void present(TrainViewModel train) {
            action.run();
        }

        @Override
        public void hide() {
            action = announceTracking;
        }

        private Runnable announceTracking = new Runnable() {
            @Override
            public void run() {
                postman.broadcast(new IAmBaseMessage());
                action = nothing;
            }
        };

        private Runnable nothing = new Runnable() {
            @Override
            public void run() {
            }
        };

        private Runnable action = announceTracking;

    }

    private class CapturePostman implements Postman {
        public Message messageBroadcast;
        private Message messagePosted;
        private NodeId messagePostedTo;

        @Override
        public void post(Message message, NodeId deliveryAddress) {
            this.messagePosted = message;
            this.messagePostedTo = deliveryAddress;
        }

        @Override
        public void broadcast(Message message) {
            this.messageBroadcast = message;
        }
    }
}
