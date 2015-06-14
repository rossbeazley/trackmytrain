package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

interface HostNode {
    void id(Result result);

    interface Result {
        void id(Postman.NodeId id);
    }
}
