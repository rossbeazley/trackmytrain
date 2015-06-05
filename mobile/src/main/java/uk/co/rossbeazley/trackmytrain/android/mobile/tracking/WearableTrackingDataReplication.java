package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import uk.co.rossbeazley.trackmytrain.android.CanTrackTrains;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;

/**
 * Created by beazlr02 on 30/05/2015.
 */
public class WearableTrackingDataReplication implements ServiceView, GoogleApiClient.ConnectionCallbacks {

    private final GoogleApiClient mGoogleApiClient;
    private final CanTrackTrains instance;

    public WearableTrackingDataReplication(Context context, CanTrackTrains instance) {
        this.instance = instance;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    public void present(TrainViewModel train) {

        PutDataMapRequest dataMap = PutDataMapRequest.create("/trackedservice");
        DataMap map = dataMap.getDataMap();
        map.putString("PLATFORM", train.platform());
        map.putString("TIME", train.scheduledTime());
        map.putString("LATENESS", train.estimatedTime());

        PutDataRequest request = dataMap.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, request);

    }

    @Override
    public void hide() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        instance.attach(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        instance.detach(this);
    }
}
