package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;
import org.robolectric.util.ActivityController;

import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Train;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.shadowOf;

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


    @Test //@Ignore("WIP")
    public void startingServiceCreatesNotification() {
        NotificationManager notificationManager = (NotificationManager) Robolectric.application.getSystemService(Context.NOTIFICATION_SERVICE);

        TrackingService trackingService = new TrackingService(){
            {
                attachBaseContext(Robolectric.application);
            }
        };

        trackingService.onCreate();

        ShadowNotificationManager shadowNotificationManager = shadowOf(notificationManager);
        assertThat(shadowNotificationManager.getNotification(1337),is(not(nullValue())));
    }

}