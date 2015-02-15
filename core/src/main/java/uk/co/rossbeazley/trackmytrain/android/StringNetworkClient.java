package uk.co.rossbeazley.trackmytrain.android;

import com.sun.net.httpserver.Authenticator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StringNetworkClient {

    static public interface Success {
        void ok(String data);
    }

    public void stringFromUrl(final String url, final Success result)
    {
        Runnable runnable = new Runnable() {
            public void run() {
                final StringBuilder total = new StringBuilder();
                HttpURLConnection con = null;
                InputStream inputStream = null;
                BufferedReader r = null;
                try
                {
                    URL ws = new URL(url);
                    con = (HttpURLConnection) ws.openConnection();
                    inputStream = con.getInputStream();
                    r = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = r.readLine()) != null)
                    {
                        total.append(line);
                    }
                }
                catch (final Exception e)
                {
                    e.printStackTrace();
                }
                finally {

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

                result.ok(urlresult);
            }
        };

        post(runnable);
    }

    private void post(Runnable runnable) {
        new Thread(runnable).start();
    }
}
