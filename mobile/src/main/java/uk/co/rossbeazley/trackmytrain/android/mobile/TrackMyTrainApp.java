package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Application;

import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class TrackMyTrainApp extends Application{

    static TrackMyTrain instance;

    public TrackMyTrainApp() {
        this(createCore());
    }

    public TrackMyTrainApp(TrackMyTrain core) {
        super();
        instance = core;
    }

    private static TrackMyTrain createCore() {
        return new TMTBuilder()
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance.attach(new ServiceTrackingNavigationController());
    }

    class ServiceTrackingNavigationController implements ServiceView {
        @Override
        public void present(Train train) {
            TrackingService.startTrackingService(TrackMyTrainApp.this);
        }


        @Override
        public void hide() {
            TrackingService.stopTrackingService(TrackMyTrainApp.this);
        }
    }
}
