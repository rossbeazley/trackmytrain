package uk.co.rossbeazley.trackmytrain.android.wear;

import android.app.Application;
import android.content.Context;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.wear.comms.HostNode;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.TrackingNotificationServiceController;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.WearNotificationService;
import uk.co.rossbeazley.trackmytrain.android.wear.trackingScreen.StartsTrackingActivity;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearAppSingleton extends Application {

    public static WearApp instance;
    public static Postman postman;

    public WearAppSingleton() {
    }

    @Override
    public void onCreate() {
        super.onCreate();


        HostNode hostnode = new HostNode();
        WearNotificationService service = new TrackingNotificationServiceController(this);
        postman = Postman.Builder.fromContext(this);
        instance = new WearApp(hostnode, postman,  service);

        instance.attach(new StartsTrackingActivity(this));


        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                postman.broadcast(new Postman.Message("EXCEPTION!!!!! "+ex.toString()));
                System.exit(-1);
            }
        });
    }


}
