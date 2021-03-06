package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import android.content.Context;

public class TrackingNotificationServiceController implements WearNotificationService {

    private final Context context;

    public TrackingNotificationServiceController(Context context) {
        this.context = context;
    }

    @Override
    public void show() {
        AndroidNotificationService.start(context);
    }

    @Override
    public void hide() {
        AndroidNotificationService.stop(context);
    }

}
