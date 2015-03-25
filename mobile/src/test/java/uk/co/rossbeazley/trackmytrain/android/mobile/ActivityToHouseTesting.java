package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;

import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

/**
* Created by beazlr02 on 25/03/2015.
*/
public class ActivityToHouseTesting extends Activity {

    public static ActivityToHouseTesting create() {
        ActivityController<ActivityToHouseTesting> actCtl = Robolectric.buildActivity(ActivityToHouseTesting.class);
        return actCtl.create()
                .start()
                .resume()
                .visible()
                .get();
    }
}
