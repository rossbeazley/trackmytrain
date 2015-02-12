package uk.co.rossbeazley.trackmytrain.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TrainRepo {

    public void service(final String serviceId, final ServiceSuccess serviceSuccess) {

        String url = "http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/service/" + URLEncoder.encode(serviceId);

        new NetworkClient().stringFromUrl(url, new NetworkClient.Success() {
            @Override
            public void ok(String data) {
                try {
                    JSONObject jtrain = new JSONObject(data);
                    Train train = createTrain(serviceId, jtrain);
                    serviceSuccess.ok(train);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    static public interface DeparturesSuccess {

        void ok(List<Train> object);
    }

    public void departures(DeparturesSuccess result) {

        Runnable task = createTask(result);
        task.run();
    }

    private Runnable createTask(final DeparturesSuccess result)
    {
        return new Runnable() {
            public void run()
            {
                new NetworkClient().stringFromUrl("http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/departures/MCO/to/SLD", new NetworkClient.Success() {
                    @Override
                    public void ok(String data) {
                        List<Train> trains = createTrainList(data);
                        result.ok(trains);
                    }
                });
            }};
    }

    private List<Train> createTrainList(String urlresult)
    {
        List<Train> trains = new ArrayList<Train>();
        try
        {
            final JSONObject jobj = new JSONObject(urlresult);
            JSONArray jtrains = jobj.getJSONArray("rows");
            for(int i=0 ; i<jtrains.length() ; i++) {
                JSONObject jtrain = jtrains.getJSONObject(i);
                Train train = createTrain(jtrain);
                trains.add(train);
            }

        }catch (JSONException e) {}



        return trains;
    }

    private Train createTrain(JSONObject jtrain) throws JSONException {
        return new Train(jtrain.getString("sid"), jtrain.getString("eta"), jtrain.getString("std"));
    }

    private Train createTrain(String serviceId, JSONObject jtrain) throws JSONException {
        return new Train(serviceId, jtrain.getString("eta"), jtrain.getString("sta"));
    }


    public interface ServiceSuccess {
        void ok(Train object);
    }
}
