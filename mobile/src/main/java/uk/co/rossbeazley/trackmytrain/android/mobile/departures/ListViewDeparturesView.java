package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

class ListViewDeparturesView implements DeparturesView {
    private final ListView listView;
    private DeparturesViewModel trains;

    public ListViewDeparturesView(FindsView findsView) {
        this((ListView) findsView.findViewById(R.id.departurelist));
    }

    public ListViewDeparturesView(ListView listView) {
        this.listView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrainViewModel train = (TrainViewModel) parent.getItemAtPosition(position);
                trains.select(train);
                ((TrainViewModelListAdapter) parent.getAdapter()).notifyDataSetChanged();
            }
        });
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

    @Override
    public void loading() {

    }

}
