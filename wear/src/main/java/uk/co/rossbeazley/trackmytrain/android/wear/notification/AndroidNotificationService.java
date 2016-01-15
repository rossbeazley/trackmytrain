package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;

public class AndroidNotificationService extends Service {

    public static final int ID = 80085;
    private final MyWearNotification notificationPresenter;

    public AndroidNotificationService() {
        notificationPresenter = new MyWearNotification();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    static public void start(Context context) {
        Intent intent = new Intent(context, AndroidNotificationService.class);
        context.startService(intent);
    }

    static public void stop(Context context) {
        Intent intent = new Intent(context, AndroidNotificationService.class);
        context.stopService(intent);
    }

    @Override
    public void onCreate() {
        WearAppSingleton.instance.attach(notificationPresenter);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        WearAppSingleton.instance.detach(notificationPresenter);
        super.onDestroy();
    }

    @TargetApi(20)
    private Notification.WearableExtender replaceSmallIconWithLargeInlineIcon() {
        Notification.WearableExtender ext;
        ext = new Notification.WearableExtender()
                .setContentIcon(R.mipmap.ic_launcher)
                .setHintHideIcon(true);
        return ext;
    }

    private class MyWearNotification implements WearNotificationService.WearNotification {
        @Override
        public void show(TrainViewModel train) {


            final Notification.Builder builder = new Notification.Builder(AndroidNotificationService.this)
                    .setContentTitle(train.platform())
                    .setContentText(train.scheduledTime() + " exp " + train.estimatedTime())
                    //.setSmallIcon(R.drawable.n_train)
                    //.addAction(R.drawable.ic_stop_tracking, "Stop Tracking", TrackingService.stopTrackingPendingIntent(service))
                    ;

            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT) {
                builder.extend(replaceSmallIconWithLargeInlineIcon());
            }

            if(train.isLate()) {
                builder.setVibrate(new long[]{87, 78, 87, 78, 87, 78, 87, 78});
            }

            NotificationManagerCompat.from(AndroidNotificationService.this).notify(ID, builder.build());

        }
    }
}
