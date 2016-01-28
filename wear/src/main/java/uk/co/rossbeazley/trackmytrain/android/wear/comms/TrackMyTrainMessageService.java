package uk.co.rossbeazley.trackmytrain.android.wear.comms;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import uk.co.rossbeazley.trackmytrain.android.wear.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.wear.MessageEnvelope;
import uk.co.rossbeazley.trackmytrain.android.wear.PostmanMessageFactory;

/**
 * Created by beazlr02 on 30/06/2015.
 */
public class TrackMyTrainMessageService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        Log.d("TMT-mobile", "-=-=-=-=-=-= onMessageReceived =-=-=-=-=-=-");
        Log.d("TMT-mobile", messageEvent.getPath());
        MessageEnvelope message = new PostmanMessageFactory().toMessage(messageEvent);
        WearAppSingleton.instance.message(message);

    }
}
