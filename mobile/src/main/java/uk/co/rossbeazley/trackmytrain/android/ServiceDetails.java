package uk.co.rossbeazley.trackmytrain.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.trainRepo.TrainRepo;

public class ServiceDetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);

        final TextView serviceIdTV = (TextView) findViewById(R.id.serviceid);

        final ListView departures = (ListView) findViewById(R.id.departurelist);

        departures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Train train = (Train) parent.getItemAtPosition(position);
                serviceIdTV.setText(train.id);
            }
        });

        findViewById(R.id.get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TrainRepo().departures(
                        new TrainRepo.DeparturesSuccess() {
                            public void ok(final List<Train> jobj) {

                                serviceIdTV.post(new Runnable() {
                                    public void run() {
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

}
