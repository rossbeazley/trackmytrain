package uk.co.rossbeazley.trackmytrain.android.wear;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

/**
 * Created by beazlr02 on 08/07/2015.
 */
public class TrackingActivityTest extends InstrumentationTestCase {

    public void test_startsTrackingActivity() {

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(TrackingActivity.class.getName(),null,false);

        TrackingActivity.launch(this.getInstrumentation().getContext());

        assertNotNull(monitor.waitForActivityWithTimeout(5000));

    }

}