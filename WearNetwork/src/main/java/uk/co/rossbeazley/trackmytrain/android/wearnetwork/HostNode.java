package uk.co.rossbeazley.trackmytrain.android.wearnetwork;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public interface HostNode {
    void id(Result result);

    void register(Postman.NodeId nodeId);

    interface Result {
        void id(Postman.NodeId id);
    }
}
