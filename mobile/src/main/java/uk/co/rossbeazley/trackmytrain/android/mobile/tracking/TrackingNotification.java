package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
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

        final Notification.Builder builder = new Notification.Builder(service)
                .setContentTitle(train.platform())
                .setContentText(train.scheduledTime() + " exp " + train.estimatedTime())
                .setSmallIcon(R.drawable.n_train)
                .addAction(R.drawable.ic_stop_tracking, "Stop Tracking", TrackingService.stopTrackingPendingIntent(service))
                ;

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT) {
            builder.extend(replaceSmallIconWithLargeInlineIcon());
        }

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH) {

            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        if(train.isLate()) {
            builder.setVibrate(new long[]{87, 78, 87, 78, 87, 78, 87, 78});
        }

        NotificationManagerCompat.from(service).notify(ID, builder.build());
    }

    @TargetApi(20)
    private Notification.WearableExtender replaceSmallIconWithLargeInlineIcon() {
        Notification.WearableExtender ext;
        ext = new Notification.WearableExtender()
                .setContentIcon(R.mipmap.ic_launcher)
                .setHintHideIcon(true);
        return ext;
    }

    @Override
    public void hide() {
        NotificationManagerCompat.from(service).cancel(ID);
    }

    @Override
    public void trackingStarted() {

    }
}
