package uk.co.rossbeazley.trackmytrain.android.mobile;

import org.junit.Test;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.MessagingTrackingPresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.StartedTrackingMessage;

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

        Postman.Message expectedMessage = new StartedTrackingMessage();
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

        Postman.Message expectedMessage = new StartedTrackingMessage();
        Postman.Message messageDelivered = postman.messageBroadcast;
        assertThat(messageDelivered, is(expectedMessage));
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
