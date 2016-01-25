package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;

public class AndroidNotificationService extends Service {

    public static final int ID = 80085;
    private final MyWearNotification notificationPresenter;

    public AndroidNotificationService() {
        broadcast("/SERVICE/CONSTRUCTED");
        notificationPresenter = new MyWearNotification(this);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder(){
            AndroidNotificationService getService() {
                return AndroidNotificationService.this;
            }
        };
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
        broadcast("/SERVICE/STARTED");
        WearAppSingleton.instance.attach(notificationPresenter);
        notificationPresenter.show(new TrainViewModel("","loading","",""));
    }

    public void broadcast(String message) {
        WearAppSingleton.postman.broadcast(new Postman.Message(message));
    }

    @Override
    public void onDestroy() {
        broadcast("/SERVICE/DESTROYED");
        stopForeground(true);
        WearAppSingleton.instance.detach(notificationPresenter);
        super.onDestroy();
    }

    private static class MyWearNotification implements WearNotificationService.WearNotification {
        private final AndroidNotificationService androidNotificationService;

        public MyWearNotification(AndroidNotificationService androidNotificationService) {

            this.androidNotificationService = androidNotificationService;
        }

        @Override
        public void show(TrainViewModel train) {


            final Notification.Builder builder = new Notification.Builder(androidNotificationService)
                    .setContentTitle(train.platform())
                    .setContentText(train.scheduledTime() + " exp " + train.estimatedTime())
                    .setSmallIcon(R.drawable.close_button)
                    //.addAction(R.drawable.ic_stop_tracking, "Stop Tracking", TrackingService.stopTrackingPendingIntent(service))
                    ;

//            NotificationManagerCompat.from(AndroidNotificationService.this).notify(ID, builder.build());

            androidNotificationService.startForeground(ID, builder.build());
        }
    }
}
