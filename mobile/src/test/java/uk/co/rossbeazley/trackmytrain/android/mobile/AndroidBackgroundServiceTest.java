package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Train;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class AndroidBackgroundServiceTest {

    @Test
    public void androidServiceStartsWhenTrackingAService() {

        Train expectedTrain = new Train("2", "10:00", "09:00", "1");
        TestTrackMyTrainApp.fakeTrackMyTrain.announceWatchedService(expectedTrain);

        Intent intent = Robolectric.getShadowApplication().getNextStartedService();
        String aClass = intent.getComponent().getClassName();
        assertThat(aClass,is(equalTo(TrackingService.class.getName())));
    }

    @Test
    public void androidServiceStopsWhenTrackingEnds() {

        TestTrackMyTrainApp.fakeTrackMyTrain.unwatch();

        Intent intent = Robolectric.getShadowApplication().getNextStoppedService();
        String aClass = intent.getComponent().getClassName();
        assertThat(aClass,is(equalTo(TrackingService.class.getName())));
    }

}