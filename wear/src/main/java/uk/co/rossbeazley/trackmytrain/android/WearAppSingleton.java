package uk.co.rossbeazley.trackmytrain.android;

import android.app.Application;
import android.content.Context;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.WearNetworkBuilder;
import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;
import uk.co.rossbeazley.trackmytrain.android.wear.WearApp;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.TrackingNotificationServiceController;
import uk.co.rossbeazley.trackmytrain.android.wear.notification.WearNotificationService;

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
        WearNotificationService service = new TrackingNotificationServiceController((Context)this);
        postman = WearNetworkBuilder.fromContext(this);
        instance = new WearApp(hostnode, postman,  service);

        instance.attach(new StartsTrackingActivity(this));
    }


}
