package uk.co.rossbeazley.trackmytrain.android.wear;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.Callable;

import uk.co.rossbeazley.trackmytrain.android.R;



/**
 * Created by beazlr02 on 30/05/2015.
 */
public class TrackingActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;

    DataApi.DataListener trackedServicePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TMT_WATCH","-=-=-=-=-=- started =-=-=-=-=-=-");

        setContentView(R.layout.tracking);

        TextView platform = (TextView) findViewById(R.id.platform);
        TextView time = (TextView) findViewById(R.id.time);
        TextView lateness = (TextView) findViewById(R.id.lateness);

        trackedServicePresenter = new TrackedServicePresenter(platform,time,lateness);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();

    }
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        localNodeId();
        Wearable.DataApi.addListener(mGoogleApiClient, trackedServicePresenter);
    }

    void localNodeId() {
        Wearable.NodeApi.getLocalNode(mGoogleApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetLocalNodeResult>() {
                    @Override
                    public void onResult(NodeApi.GetLocalNodeResult getLocalNodeResult) {
                        String nodeId = getLocalNodeResult.getNode().getId();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.DataApi.removeListener(mGoogleApiClient, trackedServicePresenter);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

}
