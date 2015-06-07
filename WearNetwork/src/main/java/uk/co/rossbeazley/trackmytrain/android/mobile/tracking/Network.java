package uk.co.rossbeazley.trackmytrain.android.mobile.tracking;

public interface Network {
    void register(Connection listener);

    void deregister(Connection listener);

    public interface Connection {
        void connected();

        void disconnected();
    }
}
