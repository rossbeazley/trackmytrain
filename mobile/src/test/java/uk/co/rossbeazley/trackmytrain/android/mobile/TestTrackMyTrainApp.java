package uk.co.rossbeazley.trackmytrain.android.mobile;

import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TestDataBuilder;
import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;

public class TestTrackMyTrainApp extends TrackMyTrainApp {
    public TestTrackMyTrainApp() {
        super(new TMTBuilder().with(new NetworkClient() {
            @Override
            public void requestString(Request request, final Response response) {
                final Train train = new Train("1", "", "", "");
                final Train train1 = new Train("2", "", "", "");

                Runnable runnable = new Runnable() {
                    public void run() {
                        response.ok(TestDataBuilder.jsonForTrains(train, train1));
                    }
                };
                //new Thread(runnable).start();   //cant get threads to work proper with robolectric
                runnable.run();
            }
        }).build());


        getMainLooper();
    }
}
