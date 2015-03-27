package uk.co.rossbeazley.trackmytrain.android;

public interface NetworkClient {

    void requestString(Request request, Response response);

    public static interface Response {
        public void ok(String response);

        void error(String error);
    }

    public static interface Request {
        public String asUrlString();
    }

}
