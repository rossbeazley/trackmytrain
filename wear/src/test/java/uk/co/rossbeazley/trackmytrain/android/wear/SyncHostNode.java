package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

class SyncHostNode {
    public Postman.NodeId registeredId;

    public void id(Result result) {
        result.id(registeredId);
    }

    public void register(Postman.NodeId nodeId) {
        registeredId=nodeId;
    }

    interface Result {
        void id(Postman.NodeId id);
    }
}
