package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

import android.content.Context;

public class WearNetworkBuilder {

    public static Postman fromContext(Context context) {
        WearNetwork network = new WearNetwork(context);
        return new PostOffice(new WearPostman(network), network);
    }
}
