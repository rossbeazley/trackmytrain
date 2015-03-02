package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import uk.co.rossbeazley.trackmytrain.android.R;

public class TrackingService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    static public void startTrackingService(Context context) {
        Intent intent = new Intent(context,TrackingService.class);
        context.startService(intent);
    }

    public static void stopTrackingService(Context context) {
        Intent intent = new Intent(context,TrackingService.class);
        context.stopService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Notification not = new Notification.Builder(this).setContentTitle("Track").setContentText("Train").setSmallIcon(R.drawable.ic_launcher).build();
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1337,not);
    }
}
