package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import static uk.co.rossbeazley.trackmytrain.android.mobile.tracking.WearNetwork.*;


public class WearPostman implements Postman {

    private final WearNetwork network;

    public WearPostman(WearNetwork network) {
        this.network = network;
    }



    @Override
    public void broadcast(Message message) {
        broadcast(message.messageAsString());
    }

    private void broadcast(final String messagePathString) {

        Log.d("TMT-mobile", "-=-=-=-=-=-= sending =-=-=-=-=-=-");
        Log.d("TMT-mobile", messagePathString);
        WearNetworkTask runnable = new WearNetworkTask() {
            public void run(GoogleApiClient gac) {
                PendingResult<NodeApi.GetConnectedNodesResult> connectedNodes = Wearable.NodeApi.getConnectedNodes(gac);
                NodeApi.GetConnectedNodesResult await = connectedNodes.await();
                List<Node> nodes = await.getNodes();

                for (Node node : nodes) {
                    String id = node.getId();
                    post(id, messagePathString);
                }
            }
        };
        network.execute(runnable);
    }



    @Override
    public void post(Message message, NodeId deliveryAddress) {
        post(message.nodeIdAsString(), message.messageAsString());
    }

    private void post(final String nodeId, final String message) {
        WearNetworkTask runnable = new WearNetworkTask() {
            public void run(GoogleApiClient gac) {
                Wearable.MessageApi.sendMessage(gac, nodeId, message, new byte[0]);
            }
        };
        network.execute(runnable);
    }


}
