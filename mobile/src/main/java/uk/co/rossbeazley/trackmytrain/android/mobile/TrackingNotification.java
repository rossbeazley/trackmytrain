package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

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
    public void present(TrainViewModel train) {
        Notification not;
        not = new Notification.Builder(service)
                .setContentTitle("Platform " + train.platform)
                .setContentText(train.scheduledTime + " exp " + train.estimatedTime)
                .setSmallIcon(R.drawable.n_train)
                .setVibrate(new long[]{10, 50, 100, 50})
              //.extend( replaceSmallIconWithLargeInlineIcon() )
                .build();

        NotificationManagerCompat.from(service).notify(ID, not);
    }

    private Notification.WearableExtender replaceSmallIconWithLargeInlineIcon() {
        Notification.WearableExtender ext;
        ext = new Notification.WearableExtender()
                .setContentIcon(R.drawable.ic_train)
                .setHintHideIcon(true);
        return ext;
    }

    @Override
    public void hide() {
        NotificationManagerCompat.from(service).cancel(ID);
    }
}
