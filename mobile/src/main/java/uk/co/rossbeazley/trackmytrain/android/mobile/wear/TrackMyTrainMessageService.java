package uk.co.rossbeazley.trackmytrain.android.mobile.wear;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;
import uk.co.rossbeazley.trackmytrain.android.wear.PostmanMessageFactory;

/**
 * Created by beazlr02 on 30/06/2015.
 */
public class TrackMyTrainMessageService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        MessageEnvelope message = new PostmanMessageFactory().toMessage(messageEvent);
        TrackMyTrainApp.messageService.message(message);

    }
}
