package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class TrackingService extends Service {

    public static final int ID = 80085;
    public static final int STOP_TRACKING_ID = 666;
    public static final String STOP_TRACKING_ACTION = "STOP_TRACKING_ACTION";

    public static PendingIntent stopTrackingPendingIntent(Context context) {
        return PendingIntent.getService(context, STOP_TRACKING_ID, stopTrackingIntent(context), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static Intent stopTrackingIntent(Context context) {
        Intent intent = new Intent(STOP_TRACKING_ACTION);
        intent.setClass(context, TrackingService.class);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    static public void startTrackingService(Context context) {
        Intent intent = new Intent(context, TrackingService.class);
        context.startService(intent);
    }

    public static void stopTrackingService(Context context) {
        Intent intent = new Intent(context, TrackingService.class);
        context.stopService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Notification not;

        not = new Notification.Builder(this)
                .setContentTitle("Track My Train")
                .setContentText("...is currently tracking")
                .setSmallIcon(R.drawable.n_train)
                .addAction(R.drawable.ic_stop_tracking,"Stop Tracking", TrackingService.stopTrackingPendingIntent(this))
                .setPriority(Notification.PRIORITY_MIN)
                .setOngoing(true)
                .build();
        startForeground(ID,not);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (STOP_TRACKING_ACTION.equals(action)) {
            TrackMyTrainApp.instance.unwatch();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
