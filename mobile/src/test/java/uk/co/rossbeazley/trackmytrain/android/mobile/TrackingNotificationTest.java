package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;

import uk.co.rossbeazley.trackmytrain.android.Train;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
public class TrackingNotificationTest {


    public NotificationManager notificationManager;

    @Before
    public void setUp() throws Exception {
        notificationManager = (NotificationManager) Robolectric.application.getSystemService(Context.NOTIFICATION_SERVICE);


        Train expectedTrain = new Train("2", "10:00", "09:00", "1");
        TestTrackMyTrainApp.instance.watch("2");


        TrackMyTrainApp.instance.attach(new TrackingNotification(Robolectric.application));
    }

    @Test
    public void startingServiceCreatesNotification() {
        ShadowNotificationManager shadowNotificationManager = shadowOf(notificationManager);
        final Notification notification = shadowNotificationManager.getNotification(TrackingNotification.ID);
        assertThat(shadowOf(notification).getContentTitle(), CoreMatchers.<CharSequence>is("Platform 1"));
        assertThat(shadowOf(notification).getContentText(), CoreMatchers.<CharSequence>is("09:00 exp 10:00"));
    }


    @Test
    public void trackingEndsNotificationRemoved() {
        TestTrackMyTrainApp.instance.unwatch();

        ShadowNotificationManager shadowNotificationManager = shadowOf(notificationManager);
        final Notification notification = shadowNotificationManager.getNotification(TrackingNotification.ID);

        assertThat(notification,is(nullValue()));
    }

}