package uk.co.rossbeazley.trackmytrain.android.wear;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by beazlr02 on 30/05/2015.
 */
public class StartsActivity extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        // do my stuff
        Log.d("TMT_WATCH", "-=-=-=-=-=- msg =-=-=-=-=-=-");
        Log.d("TMT_WATCH", messageEvent.toString());

        if(messageEvent.getPath().equalsIgnoreCase("/tracking/start")) {
            Intent intent = new Intent(this,TrackingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
