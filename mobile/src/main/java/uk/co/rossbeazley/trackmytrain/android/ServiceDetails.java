package uk.co.rossbeazley.trackmytrain.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServiceDetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        final TextView tv = (TextView) findViewById(R.id.servicetime);

        final ListView departures = (ListView) findViewById(R.id.departurelist);

        departures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Train train = (Train) parent.getItemAtPosition(position);
                tv.setText(train.id);
            }
        });

        findViewById(R.id.get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TrainRepo().departures(
                        new TrainRepo.DeparturesSuccess() {
                            public void ok(final List<Train> jobj) {

                                tv.post(new Runnable(){
                                    public void run()
                                    {
                                        ArrayAdapter<Train> adapter;
                                        adapter = new ArrayAdapter<Train>(ServiceDetails.this, android.R.layout.simple_list_item_1, jobj);
                                        departures.setAdapter(adapter);

                                    }
                                });
                            }
                        }
                );
            }
        });


    }

    static public class Train {
        private final String eta;
        private final String std;
        private final String id;

        public Train(String id, String eta, String std)
        {
            this.id = id;
            this.eta = eta;
            this.std = std;
        }

        public String toString() {
            return eta + " : " + std + " : " + id + System.getProperty("line.separator");
        }

    }

    static public class TrainRepo {

        static public interface DeparturesSuccess {

            void ok(List<Train> object);
        }

        public void departures(DeparturesSuccess result) {

            Runnable task = createTask(result);
            new Thread(task).start();
        }

        private Runnable createTask(final DeparturesSuccess result)
        {
            return new Runnable() {
                public void run()
                {
                    String urlresult = stringFromUrl("http://tmt.rossbeazley.co.uk/trackmytrain/rest/api/departures/MCO/to/SLD");
                    List<Train> trains = createTrainList(urlresult);
                    result.ok(trains);
                }};
        }

        private List<Train> createTrainList(String urlresult)
        {
            List<Train> trains = new ArrayList<Train>();
            try
            {
                final JSONObject jobj = new JSONObject(urlresult);
                JSONArray jtrains = jobj.getJSONArray("rows");
                for(int i=0 ; i<jtrains.length() ; i++) {
                    JSONObject jtrain = jtrains.getJSONObject(i);
                    Train train = new Train(jtrain.getString("sid"), jtrain.getString("eta"), jtrain.getString("std"));
                    trains.add(train);
                }

            }catch (JSONException e) {}



            return trains;
        }


        private String stringFromUrl(String url)
        {

            final StringBuilder total = new StringBuilder();
            try
            {
                URL ws = new URL(url);
                HttpURLConnection con = (HttpURLConnection) ws.openConnection();
                InputStream inputStream = con.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

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

                //closeeverything
            }

            String urlresult = total.toString();


            return urlresult;

        }

    }

}
