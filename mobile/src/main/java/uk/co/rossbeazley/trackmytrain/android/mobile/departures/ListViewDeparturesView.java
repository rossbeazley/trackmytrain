package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

class ListViewDeparturesView implements DeparturesView {
    private final ListView listView;
    private final Button trackButton;
    private DeparturesViewModel trains;

    public ListViewDeparturesView(FindsView findsView) {
        this((ListView) findsView.findViewById(R.id.departurelist), (TextView) findsView.findViewById(R.id.selectedservice), (Button) findsView.findViewById(R.id.trackbutton));
    }

    public ListViewDeparturesView(ListView listView, final TextView selectedService, Button trackButton) {
        this.listView = listView;
        this.trackButton = trackButton;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainViewModel train = (TrainViewModel) parent.getItemAtPosition(position);
                trains.select(train);
                ((TrainViewModelListAdapter) parent.getAdapter()).notifyDataSetChanged();
            }
        });

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackMyTrainApp.instance.watch(String.valueOf(selectedService.getText()));
            }
        });

    }

    private void moveTrackButtonToView(View view) {
        ViewGroup oldParent = (ViewGroup) trackButton.getParent();
        oldParent.removeView(trackButton);

        ViewGroup newParent = (ViewGroup) view;
        newParent.addView(trackButton);
        trackButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void present(final DeparturesViewModel trains) {
        this.trains = trains;
        Runnable action = new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new TrainViewModelListAdapter(trains));
            }
        };

        listView.post(action);
    }

}
