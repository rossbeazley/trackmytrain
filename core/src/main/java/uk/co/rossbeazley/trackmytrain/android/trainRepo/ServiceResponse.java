package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;
import uk.co.rossbeazley.trackmytrain.android.Train;

class ServiceResponse implements NetworkClient.Response {
    private final TrainRepository.ServiceSuccess serviceSuccess;

    public ServiceResponse(TrainRepository.ServiceSuccess serviceSuccess) {
        this.serviceSuccess = serviceSuccess;
    }

    @Override
    public void ok(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            String id = obj.getString("id");
            String estimatedTime = obj.getString("estimatedTime");
            String scheduledTime = obj.getString("scheduledTime");
            String platform = obj.getString("platform");
            Train train = new Train(id, estimatedTime, scheduledTime, platform);
            serviceSuccess.result(train);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void error(String error) {

    }
}
