package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;

import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.TrackingService;

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
        TestTrackMyTrainApp.instance.watch("2");

        Intent intent = Robolectric.getShadowApplication().getNextStartedService();
        String aClass = intent.getComponent().getClassName();
        assertThat(aClass,is(equalTo(TrackingService.class.getName())));
    }

    @Test
    public void androidServiceStopsWhenTrackingEnds() {

        TestTrackMyTrainApp.instance.unwatch();

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
    public void serviceStopsNotificationRemoved() {
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

    @Test
    public void intentWithActionStopStopsTracking() {
        NotificationManager notificationManager = (NotificationManager) Robolectric.application.getSystemService(Context.NOTIFICATION_SERVICE);

        TrackingService trackingService = new TrackingService(){
            {
                attachBaseContext(Robolectric.application);
            }
        };

        trackingService.onCreate();

        CapturingServiceView serviceView = new CapturingServiceView();
        TestTrackMyTrainApp.instance.attach(serviceView);

        Intent intent = TrackingService.stopTrackingIntent(trackingService);

        trackingService.onStartCommand(intent,TrackingService.STOP_TRACKING_ID,0);

        assertThat(serviceView.STATE, is(CapturingServiceView.STATE_HIDDEN));
    }

    private static class CapturingServiceView implements ServiceView {

        public String STATE;
        static public final String STATE_HIDDEN = "hidden";
        static public final String STATE_PRESENTED = "presented";
        static public final String STATE_UNKONW = "unknown";

        public CapturingServiceView() {
            STATE = STATE_UNKONW;
        }

        @Override
        public void present(TrainViewModel train) {
            STATE = STATE_PRESENTED;
        }

        @Override
        public void hide() {
            STATE = STATE_HIDDEN;
        }
    }
}