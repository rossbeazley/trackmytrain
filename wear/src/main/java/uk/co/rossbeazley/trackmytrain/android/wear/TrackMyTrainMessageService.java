package uk.co.rossbeazley.trackmytrain.android.wear;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

/**
 * Created by beazlr02 on 30/06/2015.
 */
public class TrackMyTrainMessageService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        Postman.Message message = new PostmanMessageFactory().toMessage(messageEvent);
        WearAppSingleton.instance.message(message);

    }
}
