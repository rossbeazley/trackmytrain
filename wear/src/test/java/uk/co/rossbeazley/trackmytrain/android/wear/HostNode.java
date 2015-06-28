package uk.co.rossbeazley.trackmytrain.android.wear;

import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;

class HostNode {
    private
    Postman.NodeId registeredId = null;

    public void id(Result result) {
        if(registeredId==null) throw new RuntimeException("NO HOST REGISTERED");
        result.id(registeredId);
    }

    public void register(Postman.NodeId nodeId) {
        registeredId=nodeId;
    }

    interface Result {
        void id(Postman.NodeId id);
    }
}
