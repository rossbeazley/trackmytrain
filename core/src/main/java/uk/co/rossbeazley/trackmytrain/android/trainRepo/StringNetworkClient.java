package uk.co.rossbeazley.trackmytrain.android.trainRepo;

import com.sun.net.httpserver.Authenticator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.NetworkClient;

public class StringNetworkClient implements NetworkClient {

    public StringNetworkClient() {
        //
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
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {

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

                String urlresult = total.toString();

                response.ok(urlresult);
            }
        };

        post(runnable);
    }

    private void post(Runnable runnable) {
        new Thread(runnable).start();
    }
}
