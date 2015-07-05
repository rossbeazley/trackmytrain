package uk.co.rossbeazley.trackmytrain.android;

import android.app.Application;
import android.content.Intent;

import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;
import uk.co.rossbeazley.trackmytrain.android.wear.TrackingActivity;
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
        instance.attach(new StartsTrackingActivity());
    }


    //TODO write a connected test for this
    private class StartsTrackingActivity implements ServiceView {
        @Override
        public void present(TrainViewModel train) {
            final WearAppSingleton context = WearAppSingleton.this;
            Intent intent = new Intent(context, TrackingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }

        @Override
        public void hide() {

        }
    }
}
