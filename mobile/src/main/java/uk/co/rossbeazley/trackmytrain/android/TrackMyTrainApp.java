package uk.co.rossbeazley.trackmytrain.android;

import android.app.Application;

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


}
