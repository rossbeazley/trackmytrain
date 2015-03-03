package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;

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


    @Test
    public void startingServiceCreatesNotification() {
        NotificationManager notificationManager = (NotificationManager) Robolectric.application.getSystemService(Context.NOTIFICATION_SERVICE);

        TrackingService trackingService = new TrackingService(){
            {
                attachBaseContext(Robolectric.application);
            }
        };

        trackingService.onCreate();

        ShadowNotificationManager shadowNotificationManager = shadowOf(notificationManager);
        final Notification notification = shadowNotificationManager.getNotification(TrackingService.ID);
        assertThat(notification, is(not(nullValue())));
    }


    @Test
    public void trackingEndsNotificationRemoved() {
        NotificationManager notificationManager = (NotificationManager) Robolectric.application.getSystemService(Context.NOTIFICATION_SERVICE);

        TrackingService trackingService = new TrackingService(){
            {
                attachBaseContext(Robolectric.application);
            }
        };

        trackingService.onCreate();

        trackingService.onDestroy();

        ShadowNotificationManager shadowNotificationManager = shadowOf(notificationManager);
        final Notification notification = shadowNotificationManager.getNotification(TrackingService.ID);

        assertThat(notification,is(nullValue()));
    }

}