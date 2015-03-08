package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Application;
import android.os.Build;

import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

public class TrackMyTrainApp extends Application{

    public static TrackMyTrain instance;

    public TrackMyTrainApp() {
        System.setProperty("http.agent", "TrackMyTrain/0.1 ("+ Build.DEVICE +", " +Build.MANUFACTURER+", "+Build.MODEL+")");
        instance = new TMTBuilder()
                .with(new SharedPrefKeyValuePersistence(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance.attach(new ServiceTrackingNavigationController());
        instance.attach(new TrackingNotification(this));
    }

    class ServiceTrackingNavigationController implements ServiceView {
        @Override
        public void present(TrainViewModel train) {
            TrackingService.startTrackingService(TrackMyTrainApp.this);
        }


        @Override
        public void hide() {
            TrackingService.stopTrackingService(TrackMyTrainApp.this);
        }
    }
}
