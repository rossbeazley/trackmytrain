package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class TrackingService extends Service {

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
        final TrackingNotification trackingNotification = new TrackingNotification(this);
        TrackMyTrainApp.instance.attach(trackingNotification);
    }



    public class TrackingNotification implements ServiceView{
        public static final int ID = 1337;
        private Service service;

        public TrackingNotification(Service service) {
            this.service = service;
        }

        @Override
        public void present(Train train) {
            Notification not;
            not = new Notification.Builder(service)
                    .setContentTitle("Platform " + train.platform)
                    .setContentText(train.scheduledTime + " exp " + train.estimatedTime)
                    .setSmallIcon(R.drawable.ic_launcher)
                    //.setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();

            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(service);

            notificationManager.notify(1337, not);
            //service.startForeground(ID, not);   //wont make notification show up on wearable
        }

        @Override
        public void hide() {
            service.stopForeground(true);
        }
    }
}
