package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TrackMyTrain;
import uk.co.rossbeazley.trackmytrain.android.Train;

public class Departures extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        final TextView from = (TextView) findViewById(R.id.from);
        //from.setText("SLD");

        final TextView to = (TextView) findViewById(R.id.to);
        //to.setText("CRL");

        final ListView listView = (ListView) findViewById(R.id.departurelist);

        final TrackMyTrain instance = TrackMyTrainApp.instance;

        instance.attach(new DeparturesView() {
            @Override
            public void present(final List<Train> trains) {
                Runnable action = new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return trains.size();
                            }

                            @Override
                            public Object getItem(int position) {
                                return trains.get(position);
                            }

                            @Override
                            public long getItemId(int position) {
                                return trains.get(position).id.hashCode();
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                TextView textView = new TextView(parent.getContext());
                                textView.setText(trains.get(position).toString());
                                //textView.setTextColor(0x00ff00); //or toggle red if late, suggesting we need a view model
                                return textView;
                            }
                        });
                    }
                };

                listView.post(action);

            }
        });

        findViewById(R.id.getdepartures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Station at = Station.fromString(String.valueOf(from.getText()));
                Direction direction = Direction.to(Station.fromString(String.valueOf(to.getText())));
                instance.departures(at, direction);
            }
        });

        final TextView selectedService = (TextView) findViewById(R.id.selectedservice);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Train train = (Train) parent.getItemAtPosition(position);
                selectedService.setText(train.id);
            }
        });

//
//        final TextView serviceIdTV = (TextView) findViewById(R.id.selectedservice);
//
//        final ListView departures = (ListView) findViewById(R.id.departurelist);
//
//        departures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Train train = (Train) parent.getItemAtPosition(position);
//                serviceIdTV.setText(train.id);
//            }
//        });
//


    }

}
