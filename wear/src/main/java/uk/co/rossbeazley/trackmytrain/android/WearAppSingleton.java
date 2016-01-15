package uk.co.rossbeazley.trackmytrain.android;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.WearNetworkBuilder;
import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;
import uk.co.rossbeazley.trackmytrain.android.wear.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.wear.WearApp;
import uk.co.rossbeazley.trackmytrain.android.wear.WearNotification;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearAppSingleton extends Application {

    public static WearApp instance;

    public WearAppSingleton() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HostNode hostnode = new HostNode();
        WearNotification service = new TrackingNotificationService((Context)this);
        instance = new WearApp(hostnode, WearNetworkBuilder.fromContext(this),  service);

        instance.attach(new StartsTrackingActivity(this));
    }


    public static class TrackingNotificationService extends Service implements WearNotification{

        private final Context context;

        public TrackingNotificationService(Context context) {

            this.context = context;
        }

        @Override
        public void show() {

        }

        @Override
        public void show(TrainViewModel trainViewModel) {
            //gonna have to fire an intent :(
        }

        @Override
        public void hide() {

        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
