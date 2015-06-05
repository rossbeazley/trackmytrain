package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by beazlr02 on 05/06/2015.
 */
public class WearNetwork {

    public void execute(Runnable runnable) {
        state.execute(runnable);
    }

    interface ConnectionState {
        void informConnectedState(Connection connection);

        void execute(Runnable runnable);
    }

    private ConnectionState state;


    public interface Connection {
        void connected();

        void disconnected();
    }

    List<Connection> connectionListeners = new CopyOnWriteArrayList<>();

    public void register(Connection listener) {
        state.informConnectedState(listener);
        connectionListeners.add(listener);
    }

    public void deregister(Connection listener) {
        state.informConnectedState(listener);
        connectionListeners.remove(listener);
    }


    private final Context context;
    GoogleApiClient gac;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    WearNetwork(Context context) {
        state = new DisconnectedState();
        this.context = context.getApplicationContext();
        gac = connectToPlayServices();
    }



    private GoogleApiClient connectToPlayServices() {
        GoogleApiClient gac = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        networkConnected();
                        Log.d("TMT-mobile", "connected");
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        networkDisconnected();
                        Log.d("TMT-mobile", "connection suspended" + i);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        networkDisconnected();
                        Log.d("TMT-mobile", connectionResult.toString());
                        try {
                            Toast.makeText(context, "Failed to conenct to google play services " + connectionResult.toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("WATCH", "toasting error", e);
                        }
                    }
                })
                .build();
        gac.connect();
        return gac;
    }

    private void networkConnected() {
        state = new ConnectedState();
    }

    private void networkDisconnected() {
        state = new DisconnectedState();
    }


    private class ConnectedState implements ConnectionState {
        public ConnectedState() {
            executor = Executors.newSingleThreadExecutor();
            for (Connection connection : connectionListeners) {
                informConnectedState(connection);
            }
        }

        @Override
        public void informConnectedState(Connection connection) {
            connection.connected();
        }

        @Override
        public void execute(Runnable runnable) {
            executor.execute(runnable);
        }
    }

    private class DisconnectedState implements ConnectionState {
        public DisconnectedState() {
            for (Connection connection : connectionListeners) {
                informConnectedState(connection);
            }
            executor.shutdownNow();
        }

        @Override
        public void informConnectedState(Connection connection) {
            connection.disconnected();
        }

        @Override
        public void execute(Runnable runnable) {
            //NO-OP
        }
    }
}
