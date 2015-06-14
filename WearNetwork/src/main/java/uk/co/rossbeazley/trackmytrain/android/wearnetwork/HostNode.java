package uk.co.rossbeazley.trackmytrain.android.wearnetwork;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

public interface HostNode {
    void id(Result result);

    interface Result {
        void id(Postman.NodeId id);
    }
}
