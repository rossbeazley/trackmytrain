package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.IntentService;
import android.content.Intent;

public class TrackingService extends IntentService {

    public TrackingService() {
        super("tracking service");
    }

    public TrackingService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
