package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.Train;
import uk.co.rossbeazley.trackmytrain.android.trainRepo.StringNetworkClient;

public class TrainRepo {

    private StringNetworkClient networkClient;

    public TrainRepo() {
        networkClient = new StringNetworkClient();
    }

    public interface ServiceSuccess {
        void ok(Train object);
    }

    public void service(final String serviceId, final ServiceSuccess serviceSuccess) {
//
//        String url = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service/" + URLEncoder.encode(serviceId);
//
//        networkClient.requestString(url, new StringNetworkClient.Success() {
//            @Override
//            public void ok(String data) {
//                try {
//                    JSONObject jtrain = new JSONObject(data);
//                    Train train = new Train(serviceId, jtrain.getString("eta"), jtrain.getString("sta"), "");
//                    serviceSuccess.ok(train);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }


    /**
     *
     * departures: train and service
     * ID : serviceID
     * scheduled time (choose arrival as primary but departure when null or empty) : sta/std
     * estimated time : scheduledTime/etd
     * platform : platform
     *
     */
}
