package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Train;

/**
* Created by beazlr02 on 03/03/2015.
*/
public class TrackingNotification implements ServiceView {
    public static final int ID = 1337;
    private Context service;

    public TrackingNotification(Context service) {
        this.service = service;
    }

    @Override
    public void present(Train train) {
        Notification not;
        long[] vibratePattern = {100,500,100,900};
        not = new Notification.Builder(service)
                .setContentTitle("Platform " + train.platform)
                .setContentText(train.scheduledTime + " exp " + train.estimatedTime)
                .setSmallIcon(R.drawable.n_train)
                .setVibrate(vibratePattern)
                //.setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();

        NotificationManagerCompat.from(service).notify(ID, not);
    }

    @Override
    public void hide() {
        NotificationManagerCompat.from(service).cancel(ID);
    }
}
