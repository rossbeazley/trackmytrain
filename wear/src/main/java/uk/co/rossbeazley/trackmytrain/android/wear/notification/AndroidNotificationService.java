package uk.co.rossbeazley.trackmytrain.android.wear.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.wear.WearAppSingleton;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen.TrackingActivity;

public class AndroidNotificationService extends Service implements WearNotificationService.NotificationView {


    static public void start(Context context) {
        context.startService(new Intent(context, AndroidNotificationService.class));
    }

    static public void stop(Context context) {
        context.stopService(new Intent(context, AndroidNotificationService.class));
    }


    public static final int ID = 80085;

    public static final int STOP_TRACKING_ID = 666;

    private NotificationPresenter notificationPresenter;

    public AndroidNotificationService() {
        broadcast("/SERVICE/CONSTRUCTED");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        notificationPresenter = new NotificationPresenter(this, WearAppSingleton.instance);
    }

    private void broadcast(String message) {
        WearAppSingleton.postman.broadcast(new Postman.Message(message));
    }

    @Override
    public void onDestroy() {
        WearAppSingleton.instance.detach(notificationPresenter);
        stopForeground(true);
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            String action = intent.getAction();
            notificationPresenter.serviceAction(action);
        }
        return START_STICKY;
    }




    public void notify(String contentTitle, String contentText) {
        Notification.Action action;
        action = new Notification.Action.Builder(R.drawable.ic_menu_close_clear_cancel, "Stop Tracking", stopTrackingPendingIntent(this)).build();
        Intent intent = new Intent(this, TrackingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        final Notification.Builder builder = new Notification.Builder(this)
                .setContentIntent(PendingIntent.getActivity(this,1234,intent,PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .addAction(action)
                .setOngoing(false)
                ;

        this.startForeground(ID, builder.build());
    }

    public static PendingIntent stopTrackingPendingIntent(Context context) {
        Intent intent = new Intent(NotificationPresenter.STOP_TRACKING_ACTION);
        intent.setClass(context, AndroidNotificationService.class);
        return PendingIntent.getService(context, STOP_TRACKING_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
