package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;
class WearNetwork implements Network {

    public interface WearNetworkTask {
        void run(GoogleApiClient gac);
    }

    public void execute(WearNetworkTask runnable) {
        state.execute(runnable);
    }

    interface ConnectionState {
        void informConnectedState(Connection connection);

        void execute(WearNetworkTask runnable);
    }

    private ConnectionState state;


    List<Connection> connectionListeners = new CopyOnWriteArrayList<>();

    @Override
    public void register(Connection listener) {
        state.informConnectedState(listener);
        connectionListeners.add(listener);
    }

    @Override
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
                .addApiIfAvailable(Wearable.API)
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
        public void execute(final WearNetworkTask runnable) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    runnable.run(gac);
                }
            });
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
        public void execute(WearNetworkTask runnable) {

        }
    }
}
