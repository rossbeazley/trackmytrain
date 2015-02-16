package uk.co.rossbeazley.trackmytrain.android.trainRepo;

public interface NetworkClient {

    void requestString(Request request, Response response);

    public static interface Response {
        public void ok(String response);
    }

    public static interface Request {
        public String asUrlString();
    }

}
