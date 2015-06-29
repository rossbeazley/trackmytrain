package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.rossbeazley.trackmytrain.android.TestDataBuilder.anyTrainViewModel;

public class TrackingOnTheWearable {

    @Test
    public void
    isStartedWithABroadcastMessage() {
        CapturingPostman postman = new CapturingPostman();

        new WearableTrackingNavigationController(postman).present(anyTrainViewModel());

        Postman.BroadcastMessage trackingStartedBroadcast = TrackingStartedBroadcastMessage.createTrackingStartedBroadcastMessage();
        assertThat(postman.messageBroadcast, is(trackingStartedBroadcast));
    }

    class CapturingPostman implements Postman {
        public Message messagePosted;
        public BroadcastMessage messageBroadcast;

        @Override
        public void post(Message message) {
            messagePosted = message;
        }

        @Override
        public void broadcast(Message message) {
            messageBroadcast=message;
        }


    }

}
