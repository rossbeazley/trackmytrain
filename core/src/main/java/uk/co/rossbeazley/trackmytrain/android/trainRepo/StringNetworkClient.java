package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.co.rossbeazley.trackmytrain.android.NetworkClient;

public class StringNetworkClient implements NetworkClient {

    private final ExecutorService executor;

    public StringNetworkClient() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void get(final Request request, final Response response) {

        Runnable runnable = new NetworkRequestRunnable(request, response);

        executor.submit(runnable);
    }

    private static class NetworkRequestRunnable implements Runnable {
        private final Request request;
        private final Response response;

        public NetworkRequestRunnable(Request request, Response response) {
            this.request = request;
            this.response = response;
        }

        public void run() {

            HttpURLConnection con = null;

            try {
                con = connect();
                writeRequestBody(con);
                readResponse(con);

            } catch (Throwable e) {
                e.printStackTrace();
                response.error(e.getMessage());
            } finally {
                if (con != null) {
                    con.disconnect();
                }

            }

        }

        private void writeRequestBody(HttpURLConnection con) {
            request.output(new NetworkOutput(con));
        }

        private void readResponse(HttpURLConnection con) throws IOException {
            final StringBuilder total = new StringBuilder();
            try (
                    InputStream inputStream = con.getInputStream();
                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            ) {
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                String urlresult = total.toString();
                response.ok(urlresult);
            }
        }

        private HttpURLConnection connect() throws IOException {
            URL ws = new URL(request.asUrlString());
            HttpURLConnection con = (HttpURLConnection) ws.openConnection();
            con.setDoInput(true);
            con.setRequestMethod(request.method());
            return con;
        }

        static class NetworkOutput implements Request.Output {
            private HttpURLConnection con;

            public NetworkOutput(HttpURLConnection con) {
                this.con = con;
            }

            @Override
            public void write(String output) {
                con.setDoOutput(true);
                try (OutputStream stream = con.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));) {
                    writer.write(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
