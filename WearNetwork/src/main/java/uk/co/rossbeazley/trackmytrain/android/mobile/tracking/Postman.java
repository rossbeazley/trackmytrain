package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

public interface Postman {
    void broadcast(String messagePathString);

    void post(String nodeId, String message);
}
