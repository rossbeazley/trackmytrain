package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class DeparturesResponse implements NetworkClient.Response {
    private final TrainRepository.DeparturesSuccess result;

    public DeparturesResponse(TrainRepository.DeparturesSuccess result) {
        this.result = result;
    }

    @Override
    public void ok(String response) {
        List<Train> expectedList = parse(response);
        result.result(expectedList);
    }

    private List<Train> parse(String response) {
        List<Train> trains = new ArrayList<Train>();
        try
        {
            final JSONObject jobj = new JSONObject(response);
            JSONArray jtrains = jobj.getJSONArray("trains");
            for(int i=0 ; i<jtrains.length() ; i++) {
                JSONObject jtrain = jtrains.getJSONObject(i);
                Train train = new Train(jtrain.getString("id"), jtrain.getString("estimatedTime"), jtrain.getString("scheduledTime"), jtrain.getString("platform"));
                trains.add(train);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return trains;
    }
}
