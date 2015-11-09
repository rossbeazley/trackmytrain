package uk.co.rossbeazley.trackmytrain.android.mobile.departures;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import uk.co.rossbeazley.trackmytrain.android.CanProcessPresentTrackedTrainsCommands;
import uk.co.rossbeazley.trackmytrain.android.departures.presentation.DeparturesViewModel;
import uk.co.rossbeazley.trackmytrain.android.mobile.TrackMyTrainApp;

import static uk.co.rossbeazley.trackmytrain.android.mobile.departures.DepartureRow.createDepartureRow;
import static uk.co.rossbeazley.trackmytrain.android.mobile.departures.DepartureRow.recycleDepartureRow;

public class TrainViewModelListAdapter extends BaseAdapter {
    private final DeparturesViewModel trains;
    private final CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands;

    public TrainViewModelListAdapter(DeparturesViewModel trains, CanProcessPresentTrackedTrainsCommands canProcessPresentTrackedTrainsCommands) {
        this.trains = trains;
        this.canProcessPresentTrackedTrainsCommands = canProcessPresentTrackedTrainsCommands;
    }

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
        return trains.get(position).id().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return departureRow(convertView, parent).bind(trains.get(position), trains.selectedService(), canProcessPresentTrackedTrainsCommands);
    }

    private static DepartureRow departureRow(View convertView, ViewGroup parent) {
        return (convertView == null ? createDepartureRow(parent) : recycleDepartureRow(convertView));
    }

    @NonNull
    public static TrainViewModelListAdapter empty() {
        return new TrainViewModelListAdapter(new DeparturesViewModel(), null);
    }

}
