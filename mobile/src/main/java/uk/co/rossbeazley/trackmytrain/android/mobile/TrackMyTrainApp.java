package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Application;
import android.os.Build;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.ServiceTrackingNavigationController;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.TrackingNotification;

public class TrackMyTrainApp extends Application{

    public static TrackMyTrain instance;

    public static TrackMyTrainApp hack;
    public static long timer;
    public static Tracker tracker;

    public TrackMyTrainApp() {
        System.setProperty("http.agent", "TrackMyTrain/1.2 ("+ Build.DEVICE +", " +Build.MANUFACTURER+", "+Build.MODEL+")");
        instance = new TMTBuilder()
                .with(new SharedPrefKeyValuePersistence(this))
                .build();
        hack = this;

    }


    public Tracker getTracker() {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(TrackMyTrainApp.hack);
            return analytics.newTracker("UA-8505275-4");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        tracker = getTracker();
        instance.attach(new ServiceTrackingNavigationController(this));
        instance.attach(new TrackingNotification(this));
    }

    public static void finishTrack(long l) {
        long millis = l - timer;
        tracker.send(new HitBuilders.TimingBuilder()
                .setCategory("RUM")
                .setValue(millis)
                .setVariable("departureQuery")
                .setLabel("resultsOk")
                .build());
    }
}
