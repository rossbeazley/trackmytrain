package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

class SyncHostNode implements HostNode {
    public Postman.NodeId registeredId;

    @Override
    public void id(Result result) {
        result.id(registeredId);
    }

    @Override
    public void register(Postman.NodeId nodeId) {
        registeredId=nodeId;
    }
}
