package uk.co.rossbeazley.trackmytrain.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class Departures extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicedetails);
        final TextView from = (TextView) findViewById(R.id.from);
        from.setText("SLD");
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
