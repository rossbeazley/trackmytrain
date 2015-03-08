package uk.co.rossbeazley.trackmytrain.android.mobile;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import uk.co.rossbeazley.trackmytrain.android.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.Direction;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.Station;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;

class ListViewDeparturesView implements DeparturesView {
    private final ListView listView;

    public ListViewDeparturesView(FindsView findsView) {
        this((ListView) findsView.findViewById(R.id.departurelist), (TextView) findsView.findViewById(R.id.selectedservice), (Button) findsView.findViewById(R.id.trackbutton), (Button) findsView.findViewById(R.id.getdepartures), (TextView) findsView.findViewById(R.id.from), (TextView) findsView.findViewById(R.id.to));
    }

    public ListViewDeparturesView(ListView listView, final TextView selectedService, Button trackButton, Button getDepartures, final TextView from, final TextView to) {
        this.listView = listView;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainViewModel train = (TrainViewModel) parent.getItemAtPosition(position);
                selectedService.setText(train.id());
            }
        });

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackMyTrainApp.instance.watch(String.valueOf(selectedService.getText()));
            }
        });

        getDepartures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Station at = Station.fromString(String.valueOf(from.getText()));
                Direction direction = Direction.to(Station.fromString(String.valueOf(to.getText())));
                TrackMyTrainApp.instance.departures(at, direction);
            }
        });
    }

    @Override
    public void present(final List<TrainViewModel> trains) {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new TrainViewModelListAdapter(trains));
            }
        };

        listView.post(action);
    }

}
