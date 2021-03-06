package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Application;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.android.gms.analytics.Tracker;

import uk.co.rossbeazley.trackmytrain.android.CanPresentDepartureQueries;
import uk.co.rossbeazley.trackmytrain.android.DepartureQueryCommands;
import uk.co.rossbeazley.trackmytrain.android.TMTBuilder;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.analytics.Analytics;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesPresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.analytics.GoogleAnalytics;
import uk.co.rossbeazley.trackmytrain.android.trackedService.TrackedServicePresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.MessagingTrackingPresenter;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.ServiceTrackingNavigationController;
import uk.co.rossbeazley.trackmytrain.android.mobile.wear.MessageService;

public class TrackMyTrainApp extends Application{

    public static TrackMyTrain instance;
    public static TrackedServicePresenter trackedServicePresenter;
    public static CanPresentDepartureQueries departuresPresenter;
    public static DepartureQueryCommands departureQueryCommands;

    public static MessageService messageService;

    public TrackMyTrainApp() {
        instance = new TMTBuilder()
                .with(new SharedPrefKeyValuePersistence(this))
//                .with(new NetworkClient() {
//                    @Override
//                    public void get(Request request, Response response) {
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

        System.setProperty("http.agent", "TrackMyTrain/1.2 (" + Build.DEVICE + ", " + Build.MANUFACTURER + ", " + Build.MODEL + ")");

        trackedServicePresenter = new TrackedServicePresenter(instance);
        DeparturesPresenter departuresPresenter = new DeparturesPresenter(instance);
        TrackMyTrainApp.departuresPresenter = departuresPresenter;
        TrackMyTrainApp.departureQueryCommands = departuresPresenter;


        Postman postman = Postman.Builder.fromContext(this);

        final Analytics tracker = buildAnalytics();

        // wear module
        messageService = new MessageService(instance, tracker);
        instance.addTrackedServiceListener(new MessagingTrackingPresenter(postman));

        // ui
        instance.addTrackedServiceListener(new ServiceTrackingNavigationController(this));
    }

    @NonNull
    private Analytics buildAnalytics() {
        final com.google.android.gms.analytics.GoogleAnalytics analytics;
        analytics = com.google.android.gms.analytics.GoogleAnalytics.getInstance(this);

        analytics.setLocalDispatchPeriod(1800);
        final Tracker newTracker = analytics.newTracker("UA-8505275-4");

        newTracker.enableExceptionReporting(true);
        newTracker.enableAdvertisingIdCollection(true);
        newTracker.enableAutoActivityTracking(true);

        return new GoogleAnalytics(newTracker);
    }

}
