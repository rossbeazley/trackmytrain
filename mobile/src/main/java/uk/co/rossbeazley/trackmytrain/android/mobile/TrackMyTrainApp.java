package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Application;
import android.content.Intent;

import uk.co.rossbeazley.trackmytrain.android.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.StringNetworkClient;

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
        instance.attach(new ServiceView() {
            @Override
            public void present(Train train) {
                Intent intent = new Intent(TrackMyTrainApp.this,TrackingService.class);
                startService(intent);
            }

            @Override
            public void hide() {

            }
        });
    }
}
