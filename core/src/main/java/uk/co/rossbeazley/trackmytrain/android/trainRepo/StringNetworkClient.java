package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public void requestString(final Request request, final Response response) {

        Runnable runnable = new Runnable() {
            public void run() {
                final StringBuilder total = new StringBuilder();
                HttpURLConnection con = null;
                InputStream inputStream = null;
                BufferedReader r = null;
                try {
                    URL ws = new URL(request.asUrlString());
                    con = (HttpURLConnection) ws.openConnection();
                    con.setDoInput(true);
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
        };

        executor.submit(runnable);
    }
}
