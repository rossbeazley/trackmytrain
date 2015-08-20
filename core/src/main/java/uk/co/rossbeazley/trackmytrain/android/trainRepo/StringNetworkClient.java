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

        Runnable runnable = new NetworkRequestRunnable(request, response,"GET");

        executor.submit(runnable);
    }

    private static class NetworkRequestRunnable implements Runnable {
        private final Request request;
        private final Response response;
        private String requestMethod;

        public NetworkRequestRunnable(Request request, Response response, String requestMethod) {
            this.request = request;
            this.response = response;
            this.requestMethod = requestMethod;
        }

        public void run() {
            final StringBuilder total = new StringBuilder();
            HttpURLConnection con = null;
            InputStream inputStream = null;
            BufferedReader r = null;
            try {
                URL ws = new URL(request.asUrlString());
                con = (HttpURLConnection) ws.openConnection();
                con.setDoInput(true);
                con.setRequestMethod(requestMethod);

                request.output(new NetworkOutput(con));


                inputStream = con.getInputStream();
                r = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }

                String urlresult = total.toString();
                response.ok(urlresult);

            } catch (Throwable e) {
                e.printStackTrace();
                response.error(e.getMessage());
            } finally {
//TODO try with resources
                if (r != null) {
                    try {
                        r.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (con != null) {
                    con.disconnect();
                }

            }


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
