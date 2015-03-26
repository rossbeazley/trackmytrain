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
    private final View loading;
    private DeparturesViewModel trains;

    public ListViewDeparturesView(FindsView findsView) {
        this((ListView) findsView.findViewById(R.id.departurelist), findsView.findViewById(R.id.departurelist_loading));
    }

    public ListViewDeparturesView(ListView listView, View loading) {
        this.listView = listView;
        this.loading = loading;
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
                loading.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new TrainViewModelListAdapter(trains));
            }
        };

        listView.post(action);
    }

    @Override
    public void loading() {
        loading.post(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

    }

}
