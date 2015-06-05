package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

/**
 * Created by beazlr02 on 05/06/2015.
 */
public class WearPostman {

    private final WearNetwork network;

    public WearPostman(WearNetwork network) {
        this.network = network;
    }


    public void broadcast(final String messagePathString) {

        Log.d("TMT-mobile", "-=-=-=-=-=-= sending =-=-=-=-=-=-");
        Log.d("TMT-mobile", messagePathString);
        Runnable runnable = new Runnable() {
            public void run() {
                PendingResult<NodeApi.GetConnectedNodesResult> connectedNodes = Wearable.NodeApi.getConnectedNodes(network.gac);
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

    public void post(final String nodeId, final String message) {
        Runnable runnable = new Runnable() {
            public void run() {
                Wearable.MessageApi.sendMessage(network.gac, nodeId, message, new byte[0]);
            }
        };
        network.execute(runnable);
    }
}
