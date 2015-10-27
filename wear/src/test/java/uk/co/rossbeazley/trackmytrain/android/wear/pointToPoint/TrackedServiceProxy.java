package uk.co.rossbeazley.trackmytrain.android.wear.pointToPoint;

import uk.co.rossbeazley.trackmytrain.android.CanPresentTrackedTrains;
import uk.co.rossbeazley.trackmytrain.android.mobile.tracking.Postman;
import uk.co.rossbeazley.trackmytrain.android.trackedService.ServiceView;
import uk.co.rossbeazley.trackmytrain.android.wear.HostNode;

class TrackedServiceProxy implements CanPresentTrackedTrains {
    private final Postman postman;
    private final HostNode hostNode;

    public TrackedServiceProxy(Postman postman, HostNode hostNode) {

        this.postman = postman;
        this.hostNode = hostNode;
    }

    @Override
    public void attach(ServiceView serviceView) {
        // keep a ref to the ServiceView as it needs to receive messages back
        hostNode.id(new HostNode.Result() {
            @Override
            public void id(Postman.NodeId id) {
                postman.post(WatchServiceMessage.createWatchServiceMessage(), id);
            }
        });

    }

    @Override
    public void detach(ServiceView serviceView) {

    }
}
