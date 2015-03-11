package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

public class TrackingService extends Service {

    public static final int ID = 80085;
    public static final int STOP_TRACKING = 666;

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
                .setPriority(Notification.PRIORITY_MIN)
                .build();
        startForeground(ID,not);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //super.onStartCommand(intent, flags, startId);
        String action = intent.getAction();
        if ("STOP_TRACKING".equals(action)) {
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
