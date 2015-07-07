package uk.co.rossbeazley.trackmytrain.android;

import android.app.Application;

import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;
import uk.co.rossbeazley.trackmytrain.android.wear.WearApp;

/**
 * Created by beazlr02 on 02/07/2015.
 */
public class WearAppSingleton extends Application {

    public static WearApp instance;

    public WearAppSingleton() {
        HostNode hostnode = new HostNode();
        instance = new WearApp(hostnode);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance.attach(new StartsTrackingActivity(this));
    }


}
