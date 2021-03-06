package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import uk.co.rossbeazley.trackmytrain.android.CanProcessPresentTrackedTrainsCommands;
import uk.co.rossbeazley.trackmytrain.android.TMTError;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesView;
import uk.co.rossbeazley.trackmytrain.android.R;
import uk.co.rossbeazley.trackmytrain.android.TrainViewModel;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.FindsView;

class ListViewDeparturesView implements DeparturesView {
    private final ListView listView;
    private final View loading;
    private final CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands;
    private DeparturesViewModel trains;

    public ListViewDeparturesView(FindsView findsView, CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands) {
        this((ListView) findsView.findViewById(R.id.departurelist),
                findsView.findViewById(R.id.departurelist_loading), canProcessPresentTrackedTrainsCommands);
    }

    public ListViewDeparturesView(final ListView listView, View loading, CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands) {
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
        this.canProcessPresentTrackedTrainsCommands = canProcessPresentTrackedTrainsCommands;
    }

    @Override
    public void present(final DeparturesViewModel trains) {
        this.trains = trains;
        Runnable action = new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new TrainViewModelListAdapter(trains, canProcessPresentTrackedTrainsCommands));
            }
        };

        listView.post(action);
    }

    @Override
    public void loading() {
        loading.post(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(TrainViewModelListAdapter.empty());
                loading.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void error(final TMTError error) {
        loading.post(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(TrainViewModelListAdapter.empty());
                loading.setVisibility(View.GONE);
                Toast.makeText(loading.getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

}
