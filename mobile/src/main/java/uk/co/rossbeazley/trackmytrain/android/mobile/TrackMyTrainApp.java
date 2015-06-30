package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Application;
import android.os.Build;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.mobile.departures.PerfMonitoringView;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.ServiceTrackingNavigationController;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.TrackingNotification;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.DeparturesFromToRequest;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.ServiceDetailsRequest;

public class TrackMyTrainApp extends Application{

    public static TrackMyTrain instance;

    public TrackMyTrainApp() {
        System.setProperty("http.agent", "TrackMyTrain/1.2 ("+ Build.DEVICE +", " +Build.MANUFACTURER+", "+Build.MODEL+")");
        instance = new TMTBuilder()
                .with(new SharedPrefKeyValuePersistence(this))
//                .with(new NetworkClient() {
//                    @Override
//                    public void requestString(Request request, Response response) {
//                        if(request instanceof DeparturesFromToRequest) {
//                            response.ok("{\"error\":\"\"," +
//                                    "\"trains\":[" +
//                                    "{\"id\":\"asd\"," +
//                                    "\"scheduledTime\": \"12:01\"," +
//                                    "\"estimatedTime\": \"12:02\"," +
//                                    "\"platform\": \"2\"" +
//                                    "}" +
//                                    "]}");
//                        } else if(request instanceof ServiceDetailsRequest) {
//                            response.ok("{\"id\":\"asd\"," +
//                                    "\"scheduledTime\": \"12:01\"," +
//                                    "\"estimatedTime\": \"12:03\"," +
//                                    "\"platform\": \"2\"" +
//                                    "}");
//                        }
//                    }
//                })
                .build();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance.attach(new ServiceTrackingNavigationController(this));
        instance.attach(new TrackingNotification(this));
        instance.attach(new PerfMonitoringView(this));
    }

}
