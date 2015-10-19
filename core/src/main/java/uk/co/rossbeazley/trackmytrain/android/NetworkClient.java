package uk.co.rossbeazley.trackmytrain.android;

public interface NetworkClient {

    void get(Request request, Response response);

    interface Response {
        void ok(String response);

        void error(String error);
    }

    interface Request {
        String asUrlString();

        void output(Output output);

        String method();

        interface Output {
            void write(String output);
        }

        String GET = "get";
        String POST = "post";
    }

}
