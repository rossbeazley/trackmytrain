package uk.co.rossbeazley.trackmytrain.android;

import android.app.Application;

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
        WearNotification service = new WearNotification() {
            @Override
            public void show() {

            }

            @Override
            public void show(TrainViewModel trainViewModel) {

            }
        };
        instance = new WearApp(hostnode, WearNetworkBuilder.fromContext(this),  service);

        instance.attach(new StartsTrackingActivity(this));
    }


}
