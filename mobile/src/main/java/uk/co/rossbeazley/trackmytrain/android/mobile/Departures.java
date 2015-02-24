package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.R;

public class Departures extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        final TextView from = (TextView) findViewById(R.id.from);
        //from.setText("SLD");

        final TextView to = (TextView) findViewById(R.id.from);
        //to.setText("CRL");

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
